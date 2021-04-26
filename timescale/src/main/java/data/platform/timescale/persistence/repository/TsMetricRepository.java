package data.platform.timescale.persistence.repository;

import data.platform.timescale.persistence.mapping.MetricEO;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@ConditionalOnBean(name = "timeScaleConfig")
@Slf4j
@Repository
@RequiredArgsConstructor
public class TsMetricRepository {

    final DatabaseClient databaseClient;

    private final static String ID_SQL = "SELECT nextval('timescale_seq')";

    private final static String CREATE_SQL = "INSERT INTO metric(name) values(:name)";

    private final static String QUERY_SQL = "SELECT * FROM metric WHERE name = :name";

    public static final BiFunction<Row, RowMetadata, MetricEO> MAPPING_FUNCTION = (row, rowMetaData) -> MetricEO.builder()
            .id(row.get("id", Integer.class))
            .name(row.get("name", String.class))
            .build();

    public Mono<Integer> save(String metricName) {
        return this.databaseClient.sql(CREATE_SQL)
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                .bind("name", metricName)
                .fetch()
                .first()
                .map(r -> (Integer) r.get("id"))
                .doOnNext(id -> log.info("create metric id is: [{}], name is: [{}].", id, metricName));
    }

    public Mono<MetricEO> findByName(String name) {
        return databaseClient.sql(QUERY_SQL)
                .bind("name", name)
                .map(MAPPING_FUNCTION)
                .one();
    }
}
