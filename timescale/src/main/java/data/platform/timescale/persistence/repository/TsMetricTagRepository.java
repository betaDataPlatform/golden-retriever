package data.platform.timescale.persistence.repository;

import data.platform.timescale.persistence.mapping.MetricTagEO;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@ConditionalOnBean(name = "timeScaleConfig")
@Slf4j
@Repository
@RequiredArgsConstructor
public class TsMetricTagRepository {

    final DatabaseClient databaseClient;

    private final static String CREATE_SQL = "INSERT INTO metric_tag(metric_id, tag_name, tag_value, tag_id) values(:metricId, :tagName, :tagValue, :tagId)";

    private final static String FIND_ALL_SQL = "SELECT mt.*, m.name as metric, t.tag_json as tag FROM metric_tag mt LEFT JOIN metric m ON m.id = mt.metric_id LEFT JOIN tag t on t.id = mt.tag_id";

    private final static String QUERY_SQL = "SELECT mt.*, m.name as metric, t.tag_json as tag FROM metric_tag mt LEFT JOIN metric m ON m.id = mt.metric_id LEFT JOIN tag t on t.id = mt.tag_id WHERE m.name = :metric AND mt.tag_name = :tagName AND mt.tag_value = :tagValue AND t.id = :tagId";

    private final static String UPDATE_SQL = "UPDATE metric_tag SET tag_id = :tagId WHERE metric_id = :metricId AND tag_name = :tagName AND tag_value = :tagValue";

    public static final BiFunction<Row, RowMetadata, MetricTagEO> MAPPING_FUNCTION = (row, rowMetaData) -> MetricTagEO.builder()
            .metricId(row.get("metric_id", Integer.class))
            .metric(row.get("metric", String.class))
            .tagName(row.get("tag_name", String.class))
            .tagValue(row.get("tag_value", String.class))
            .tagId(row.get("tag_id", Integer.class))
            .tag(row.get("tag", String.class))
            .build();

    public Mono<MetricTagEO> save(MetricTagEO eo) {
        return databaseClient.sql(CREATE_SQL)
                .bind("metricId", eo.getMetricId())
                .bind("tagName", eo.getTagName())
                .bind("tagValue", eo.getTagValue())
                .bind("tagId", eo.getTagId())
                .fetch()
                .first()
                .thenReturn(eo)
                .doOnNext(id -> log.info("create metricTag metric is: [{}], tagName is: [{}], tagValue is: [{}], tag is: [{}].", eo.getMetric(), eo.getTagName(), eo.getTagValue(), eo.getTag()));
    }

    public Flux<MetricTagEO> findAll() {
        return databaseClient.sql(FIND_ALL_SQL)
                .map(MAPPING_FUNCTION)
                .all();
    }

    public Mono<MetricTagEO> findByMetricAndTag(String metric, String tagName, String tagValue, Integer tagId) {
        return databaseClient.sql(QUERY_SQL)
                .bind("metric", metric)
                .bind("tagName", tagName)
                .bind("tagValue", tagValue)
                .bind("tagId", tagId)
                .map(MAPPING_FUNCTION)
                .one();
    }

    public Mono<Integer> update(Integer metricId, String tagName, String tagValue, Integer tagId) {
        return this.databaseClient.sql(UPDATE_SQL)
                .bind("tagId", tagId)
                .bind("metricId", metricId)
                .bind("tagName", tagName)
                .bind("tagValue", tagValue)
                .fetch()
                .rowsUpdated();
    }
}
