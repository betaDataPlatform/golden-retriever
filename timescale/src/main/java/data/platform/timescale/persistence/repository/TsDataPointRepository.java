package data.platform.timescale.persistence.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.platform.common.query.QueryAggregatorUnit;
import data.platform.timescale.internal.cache.TsCacheService;
import data.platform.timescale.persistence.mapping.DataPointEO;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.r2dbc.spi.Statement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.BiFunction;

@ConditionalOnBean(name = "timeScaleConfig")
@Slf4j
@Repository
@RequiredArgsConstructor
public class TsDataPointRepository {

    final DatabaseClient databaseClient;

    final TsCacheService tsCacheService;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private final static String CREATE_SQL = "INSERT INTO data_point(event_time, metric_id, tag_id, value) values($1, $2, $3, $4)";

    private final static String QUERY_SQL = "SELECT * FROM data_point WHERE metric_id = :metricId AND tag_id = :tagId AND event_time >= :beginTime AND event_time <= :endTime ORDER BY event_time asc";

    private final static String FUNCTION_SQL = "SELECT %s(value) as value FROM data_point WHERE metric_id = :metricId AND tag_id = :tagId AND event_time >= :beginTime AND event_time <= :endTime";

    public static final BiFunction<Row, RowMetadata, DataPointEO> MAPPING_FUNCTION = (row, rowMetaData) -> DataPointEO.builder()
            .eventTime(row.get("event_time", Date.class))
            .value(row.get("value", Double.class))
            .metricId(row.get("metric_id", Integer.class))
            .tagId(row.get("tag_id", Integer.class))
            .build();

    public static final BiFunction<Row, RowMetadata, DataPointEO> FUNCTION_MAPPING_FUNCTION = (row, rowMetaData) -> DataPointEO.builder()
            .value(row.get("value", Double.class))
            .build();

    public Flux<String> saveAll(List<DataPointEO> eos) {
        if (eos.size() == 0) {
            return Flux.empty();
        }

        return databaseClient.inConnectionMany(connection -> {
            Statement statement = connection.createStatement(CREATE_SQL).returnGeneratedValues("metric_id");
            for (DataPointEO eo : eos) {
                statement.bind(0, eo.getEventTime())
                        .bind(1, eo.getMetricId())
                        .bind(2, eo.getTagId())
                        .bind(3, eo.getValue())
                        .add();
            }

            return Flux.from(statement.execute())
                    .flatMap(result -> result.map((row, rowMetaData) ->
                            row.get("metric_id").toString()));
                    //.doOnNext(id -> log.info("create dataPoint id is: [{}].", id));
        });
    }

    public Flux<DataPointEO> queryDataPoint(String metric, String tagJson, Date beginTime, Date endTime) {
        Integer metricId = tsCacheService.getMetricId(metric).get();
        Integer tagId = tsCacheService.getTagId(tagJson).get();
        return databaseClient.sql(QUERY_SQL)
                .bind("metricId", metricId)
                .bind("tagId", tagId)
                .bind("beginTime", beginTime)
                .bind("endTime", endTime)
                .map(MAPPING_FUNCTION)
                .all()
                .map(dataPointEO -> {
                    dataPointEO.setMetric(metric);
                    dataPointEO.setTagJson(parseJson(tagJson));
                    return dataPointEO;
                })
                .filter(dataPointEO -> Objects.nonNull(dataPointEO.getValue()));
    }

    public Flux<DataPointEO> functionDataPoint(QueryAggregatorUnit aggregatorUnit, String metric, String tagJson, Date beginTime, Date endTime) {
        Integer metricId = tsCacheService.getMetricId(metric).get();
        Integer tagId = tsCacheService.getTagId(tagJson).get();
        String sql = String.format(FUNCTION_SQL, aggregatorUnit.name());
        return databaseClient.sql(sql)
                .bind("metricId", metricId)
                .bind("tagId", tagId)
                .bind("beginTime", beginTime)
                .bind("endTime", endTime)
                .map(FUNCTION_MAPPING_FUNCTION)
                .all()
                .map(dataPointEO -> {
                    dataPointEO.setEventTime(beginTime);
                    dataPointEO.setMetricId(metricId);
                    dataPointEO.setMetric(metric);
                    dataPointEO.setTagId(tagId);
                    dataPointEO.setTagJson(parseJson(tagJson));
                    return dataPointEO;
                })
                .filter(dataPointEO -> Objects.nonNull(dataPointEO.getValue()));
    }

    private Map<String, String> parseJson(String json) {
        Map<String, String> tagJson = new HashMap<>();
        try {
            tagJson = objectMapper.readValue(json, Map.class);
        } catch (Exception ex) {
            log.error("", ex);
        }
        return tagJson;
    }
}
