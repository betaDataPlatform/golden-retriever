package data.platform.timescale.internal.query;

import data.platform.common.query.QueryAggregatorUnit;
import data.platform.common.query.QueryBuilder;
import data.platform.common.response.DataPoint;
import data.platform.common.response.QueryResult;
import data.platform.common.response.QueryResults;
import data.platform.common.response.Result;
import data.platform.common.service.query.MetricResultQueryService;
import data.platform.timescale.internal.cache.TsCacheService;
import data.platform.timescale.persistence.mapping.DataPointEO;
import data.platform.timescale.persistence.repository.TsDataPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@ConditionalOnBean(name = "timeScaleConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class TsMetricResultQueryServiceImpl implements MetricResultQueryService {

    final TsCacheService tsCacheService;

    final TsDataPointRepository tsDataPointRepository;

    @Override
    public Mono<QueryResults> query(QueryBuilder queryBuilder) {
        Date beginTime = new Date(queryBuilder.getBeginDate());
        Date endTime = new Date(queryBuilder.getEndDate());

        // 每一个QueryMetric对应一个QueryResult
        // QueryMetric解析成metric+tags+function, 通过数据库获取到原始值,返回类型为Flux<DataPointEO>
        // 如果存在group, 进行分组，不支持这个操作
        return Flux.fromIterable(queryBuilder.getMetrics())
                .flatMap(queryMetric -> {
                    // 指标名称
                    String metric = queryMetric.getMetric();

                    // 要查询的标签
                    Set<String> tagJsons = new HashSet<>();

                    if (queryMetric.getTags().size() == 0) {
                        // 只通过指标查询
                        tagJsons.addAll(tsCacheService.matchingTagByMetric(metric));
                    } else {
                        // 获取需要查询的指标以及对应的标签
                        List<Map<String, String>> queryTags = getMetricTags(queryMetric);
                        for (Map<String, String> tagMap : queryTags) {
                            tagJsons.addAll(tsCacheService.matchingTag(metric, tagMap));
                        }
                    }

                    // 标签分组
                    List<String> groupBys = new ArrayList<>();
                    if (Objects.nonNull(queryMetric.getGroupers())) {
                        queryMetric.getGroupers().forEach(queryGroupBy -> groupBys.addAll(queryGroupBy.getTags()));
                    }

                    // 查询函数，只支持一个，不支持多个查询函数
                    QueryAggregatorUnit aggregatorUnit = getQueryAggregatorUnit(queryMetric);

                    Flux<Result> resultFlux = queryByMetricAndTags(metric, tagJsons, groupBys, aggregatorUnit, beginTime, endTime);
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
     * @param beginTime      开始时间
     * @param endTime        结束时间
     * @return
     */
    private Flux<Result> queryByMetricAndTags(String metric, Set<String> tagJsons, List<String> groupBys, QueryAggregatorUnit aggregatorUnit,
                                              Date beginTime, Date endTime) {
        Flux<DataPointEO> dataPointEOFlux = Flux.fromIterable(tagJsons)
                .flatMap(tagJson -> {
                    if (aggregatorUnit == QueryAggregatorUnit.PLAIN) {
                        return tsDataPointRepository.queryDataPoint(metric,
                                tagJson, beginTime, endTime);
                    } else {
                        return tsDataPointRepository.functionDataPoint(aggregatorUnit, metric,
                                tagJson, beginTime, endTime);
                    }
                });
        Flux<Result> resultFlux;
        if (groupBys.size() == 1) {
            resultFlux = dataPointEOFlux
                    .groupBy(dataPointEO -> dataPointEO.getTagJson().get(groupBys.get(0)))
                    .flatMap(keyFlux -> keyFlux.collectList()
                            .map(eos -> new Result(metric, getTags(eos), getDataPoints(eos, aggregatorUnit))));
        } else if (groupBys.size() == 2) {
            resultFlux = dataPointEOFlux
                    .groupBy(dataPointEO -> Tuples.of(dataPointEO.getTagJson().get(groupBys.get(0)), dataPointEO.getTagJson().get(groupBys.get(1))))
                    .flatMap(keyFlux -> keyFlux.collectList()
                            .map(eos -> new Result(metric, getTags(eos), getDataPoints(eos, aggregatorUnit))));
        } else if (groupBys.size() == 3) {
            resultFlux = dataPointEOFlux
                    .groupBy(dataPointEO -> Tuples.of(dataPointEO.getTagJson().get(groupBys.get(0)),
                            dataPointEO.getTagJson().get(groupBys.get(1)),
                            dataPointEO.getTagJson().get(groupBys.get(2))))
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
            for (Map.Entry<String, String> entry : eo.getTagJson().entrySet()) {
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
            dataPoint.setTimestamp(eos.get(0).getEventTime().getTime());
            dataPoint.setValue(value);
            return Arrays.asList(dataPoint);
        }
    }
}
