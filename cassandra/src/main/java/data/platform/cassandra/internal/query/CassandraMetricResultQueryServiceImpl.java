package data.platform.cassandra.internal.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.platform.cassandra.domain.QueryTime;
import data.platform.cassandra.infra.persistence.mapping.DataPointEO;
import data.platform.cassandra.infra.persistence.repository.CassandraDataPointRepository;
import data.platform.cassandra.internal.cache.CassandraCacheService;
import data.platform.common.query.QueryAggregatorUnit;
import data.platform.common.query.QueryBuilder;
import data.platform.common.response.QueryResult;
import data.platform.common.response.QueryResults;
import data.platform.common.response.Result;
import data.platform.common.service.query.MetricResultQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        List<QueryTime> queryTimes = QueryTime.getQueryTimeSpan(covertToLocalDateTime(beginTime), covertToLocalDateTime(endTime));

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

                    // 查询函数，只支持一个，不支持多个查询函数
                    QueryAggregatorUnit aggregatorUnit = getQueryAggregatorUnit(queryMetric);
                    Flux<Result> resultFlux = queryByMetricAndTags(metric, tagJsons, aggregatorUnit, queryTimes);
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
    private Flux<Result> queryByMetricAndTags(String metric, Set<String> tagJsons, QueryAggregatorUnit aggregatorUnit,
                                              List<QueryTime> queryTimes) {

        Flux<Result> resultFlux = Flux.fromIterable(tagJsons)
                .map(tagJson -> new Result(metric, tagJson))
                .flatMap(result -> {
                    Flux<String> sqlFlux = Flux.fromIterable(queryTimes)
                            .map(queryTime -> {
                                // key是按照天存储
                                String sql;
                                if (aggregatorUnit == QueryAggregatorUnit.PLAIN) {
                                    sql = String.format(QUERY_SQL, metric, result.getTagJson(), queryTime.getDay(), timeFormatter.format(queryTime.getStartOffSet()), timeFormatter.format(queryTime.getEndOffSet()));
                                } else {
                                    sql = String.format(FUNCTION_SQL, aggregatorUnit.name(), metric, result.getTagJson(), queryTime.getDay(), timeFormatter.format(queryTime.getStartOffSet()), timeFormatter.format(queryTime.getEndOffSet()));
                                }
                                //log.info(sql);
                                return sql;
                            });
                    LocalDate day = LocalDate.from(dayFormatter.parse(queryTimes.get(0).getDay()));

                    Flux<GroupedFlux<Integer, String>> groups = sqlFlux
                            .groupBy(sql -> sql.hashCode() % queryThreadNum)
                            .publishOn(Schedulers.boundedElastic());

                    Flux<DataPointEO> dataPointEOFlux = null;
                    if (aggregatorUnit == QueryAggregatorUnit.PLAIN) {
                        dataPointEOFlux = cassandraDataPointRepository.find(groups);
                    } else {
                        dataPointEOFlux = cassandraDataPointRepository.statFunction(aggregatorUnit, groups, day, queryTimes.get(0).getStartOffSet());
                    }

                    return dataPointEOFlux
                            .filter(eo -> Objects.nonNull(eo.getDataPointKey().getMetric()))
                            .map(eo -> eo.toDataPoint())
                            .collectList()
                            .map(dataPoints -> new Result(metric, getTags(result.getTagJson()), dataPoints));
                });
        return resultFlux;
    }

    private Map<String, List<String>> getTags(String tagJson) {
        Map<String, List<String>> tags = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> tagJsonMap = objectMapper.readValue(tagJson, Map.class);
            for (Map.Entry<String, String> entry : tagJsonMap.entrySet()) {
                tags.put(entry.getKey(), Arrays.asList(entry.getValue()));
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
        return tags;
    }

    private LocalDateTime covertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
