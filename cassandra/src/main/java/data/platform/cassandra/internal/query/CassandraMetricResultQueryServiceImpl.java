package data.platform.cassandra.internal.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.platform.cassandra.domain.QueryTime;
import data.platform.cassandra.infra.persistence.mapping.DataPointEO;
import data.platform.cassandra.infra.persistence.repository.CassandraDataPointRepository;
import data.platform.cassandra.internal.cache.CassandraCacheService;
import data.platform.common.query.QueryAggregatorUnit;
import data.platform.common.query.QueryBuilder;
import data.platform.common.response.DataPoint;
import data.platform.common.response.QueryResult;
import data.platform.common.response.QueryResults;
import data.platform.common.response.Result;
import data.platform.common.service.query.MetricResultQueryService;
import data.platform.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@ConditionalOnBean(name = "cassandraConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class CassandraMetricResultQueryServiceImpl implements MetricResultQueryService {

    @Value("${query.thread}")
    private int queryThreadNum;

    final CassandraCacheService cassandraCacheService;

    final CassandraDataPointRepository cassandraDataPointRepository;

    private static final String QUERY_SQL = "SELECT * FROM data_point where metric='%s' and tag_json='%s' and day='%s' and offset >='%s' and offset <='%s'";

    private static final String FUNCTION_SQL = "SELECT metric, tag_json, day, offset, %s(value) as value FROM data_point where metric='%s' and tag_json='%s' and day='%s' and offset >='%s' and offset <='%s'";

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Mono<QueryResults> query(QueryBuilder queryBuilder) {
        Date beginTime = new Date(queryBuilder.getBeginDate());
        Date endTime = new Date(queryBuilder.getEndDate());
        // 过滤查询开始时间，查询结束时间
        List<QueryTime> queryTimes = QueryTime.getQueryTimeSpan(DateUtil.getDateTimeOfDate(beginTime),
                DateUtil.getDateTimeOfDate(endTime));

        return Flux.fromIterable(queryBuilder.getMetrics())
                .flatMap(queryMetric -> {
                    // 指标名称
                    String metric = queryMetric.getMetric();
                    // 获取需要查询的指标以及对应的标签
                    List<Map<String, String>> queryTags = getMetricTags(queryMetric);
                    // 要查询的标签
                    Set<String> tagJsons = new HashSet<>();
                    for (Map<String, String> tagMap : queryTags) {
                        tagJsons.addAll(cassandraCacheService.matchingTag(metric, tagMap));
                    }
                    // 分组标签
                    List<String> groupBys = new ArrayList<>();
                    if (Objects.nonNull(queryMetric.getGroupers())) {
                        queryMetric.getGroupers().forEach(queryGroupBy -> groupBys.addAll(queryGroupBy.getTags()));
                    }

                    // 查询函数，只支持一个，不支持多个查询函数
                    QueryAggregatorUnit aggregatorUnit = getQueryAggregatorUnit(queryMetric);
                    Flux<Result> resultFlux = queryByMetricAndTags(metric, tagJsons, groupBys, aggregatorUnit, queryTimes);
                    return resultFlux.collectList().map(results -> new QueryResult(results,
                            results.stream().map(result -> result.getDataPoints().size())
                                    .collect(Collectors.summingInt(Integer::intValue))));
                })
                .collectList()
                .map(queryResult -> new QueryResults(queryResult));
    }

    /**
     * @param metric         指标
     * @param tagJsons       标签集合
     * @param aggregatorUnit 统计函数
     * @param queryTimes     查询时间
     * @return
     */
    private Flux<Result> queryByMetricAndTags(String metric, Set<String> tagJsons, List<String> groupBys, QueryAggregatorUnit aggregatorUnit,
                                              List<QueryTime> queryTimes) {

        Flux<DataPointEO> dataPointEOFlux = Flux.fromIterable(tagJsons)
                .flatMap(tagJson -> {
                    Flux<String> sqlFlux = Flux.fromIterable(queryTimes)
                            .map(queryTime -> {
                                // key是按照天存储
                                String sql;
                                if (aggregatorUnit == QueryAggregatorUnit.PLAIN) {
                                    sql = String.format(QUERY_SQL, metric, tagJson, queryTime.getDay(), timeFormatter.format(queryTime.getStartOffSet()), timeFormatter.format(queryTime.getEndOffSet()));
                                } else {
                                    sql = String.format(FUNCTION_SQL, aggregatorUnit.name(), metric, tagJson, queryTime.getDay(), timeFormatter.format(queryTime.getStartOffSet()), timeFormatter.format(queryTime.getEndOffSet()));
                                }
                                //log.info(sql);
                                return sql;
                            });
                    LocalDate day = LocalDate.from(dayFormatter.parse(queryTimes.get(0).getDay()));

                    Flux<GroupedFlux<Integer, String>> groups = sqlFlux
                            .groupBy(sql -> sql.hashCode() % queryThreadNum)
                            .publishOn(Schedulers.boundedElastic());

                    if (aggregatorUnit == QueryAggregatorUnit.PLAIN) {
                        return cassandraDataPointRepository.find(groups);
                    } else {
                        return cassandraDataPointRepository.statFunction(aggregatorUnit, groups, day, queryTimes.get(0).getStartOffSet());
                    }
                });

        Flux<Result> resultFlux;
        if (groupBys.size() == 1) {
            resultFlux = dataPointEOFlux
                    .groupBy(dataPointEO -> parseJson(dataPointEO.getDataPointKey().getTagJson()).get(groupBys.get(0)))
                    .flatMap(keyFlux -> keyFlux.collectList()
                            .map(eos -> new Result(metric, getTags(eos), getDataPoints(eos, aggregatorUnit))));
        } else if (groupBys.size() == 2) {
            resultFlux = dataPointEOFlux
                    .groupBy(dataPointEO -> Tuples.of(parseJson(dataPointEO.getDataPointKey().getTagJson()).get(groupBys.get(0)),
                            parseJson(dataPointEO.getDataPointKey().getTagJson()).get(groupBys.get(1))))
                    .flatMap(keyFlux -> keyFlux.collectList()
                            .map(eos -> new Result(metric, getTags(eos), getDataPoints(eos, aggregatorUnit))));
        } else if (groupBys.size() == 3) {
            resultFlux = dataPointEOFlux
                    .groupBy(dataPointEO -> Tuples.of(parseJson(dataPointEO.getDataPointKey().getTagJson()).get(groupBys.get(0)),
                            parseJson(dataPointEO.getDataPointKey().getTagJson()).get(groupBys.get(1)),
                            parseJson(dataPointEO.getDataPointKey().getTagJson()).get(groupBys.get(2))))
                    .flatMap(keyFlux -> keyFlux.collectList()
                            .map(eos -> new Result(metric, getTags(eos), getDataPoints(eos, aggregatorUnit))));
        } else {
            resultFlux = dataPointEOFlux.collectList()
                    .map(eos -> new Result(metric, getTags(eos), getDataPoints(eos, aggregatorUnit)))
                    .flatMapMany(Flux::just);
        }

        return resultFlux;
    }

    private Map<String, Set<String>> getTags(List<DataPointEO> eos) {
        Map<String, Set<String>> tags = new HashMap<>();
        eos.forEach(eo -> {
            Map<String, String> tag = parseJson(eo.getDataPointKey().getTagJson());
            for (Map.Entry<String, String> entry : tag.entrySet()) {
                tags.putIfAbsent(entry.getKey(), new HashSet<>());
                tags.get(entry.getKey()).add(entry.getValue());
            }
        });
        return tags;
    }

    private List<DataPoint> getDataPoints(List<DataPointEO> eos, QueryAggregatorUnit aggregatorUnit) {
        if (aggregatorUnit == QueryAggregatorUnit.PLAIN) {
            return eos.stream().map(eo -> eo.toDataPoint()).collect(Collectors.toList());
        } else {
            Double value = null;
            DoubleStream valueStream = eos.stream().mapToDouble(eo -> eo.getValue());
            if (aggregatorUnit == QueryAggregatorUnit.AVG) {
                value = valueStream.average().getAsDouble();
            } else if (aggregatorUnit == QueryAggregatorUnit.MAX) {
                value = valueStream.max().getAsDouble();
            } else if (aggregatorUnit == QueryAggregatorUnit.MIN) {
                value = valueStream.min().getAsDouble();
            } else if (aggregatorUnit == QueryAggregatorUnit.SUM) {
                value = valueStream.sum();
            }
            DataPoint dataPoint = new DataPoint();
            LocalDateTime localDateTime = LocalDateTime.of(eos.get(0).getDataPointKey().getPartition(),eos.get(0).getDataPointKey().getOffset());
            Date eventTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            dataPoint.setTimestamp(eventTime.getTime());
            dataPoint.setValue(value);
            return Arrays.asList(dataPoint);
        }
    }

    private Map<String, String> parseJson(String json) {
        Map<String, String> tagJson = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            tagJson = objectMapper.readValue(json, Map.class);
        } catch (Exception ex) {
            log.error("", ex);
        }
        return tagJson;
    }
}
