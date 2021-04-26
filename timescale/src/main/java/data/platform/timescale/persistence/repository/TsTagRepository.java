package data.platform.timescale.persistence.repository;

import data.platform.timescale.persistence.mapping.TagEO;
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
public class TsTagRepository {

    final DatabaseClient databaseClient;

    private final static String ID_SQL = "SELECT nextval('timescale_seq')";

    private final static String CREATE_SQL = "INSERT INTO tag(tag_json) values(:tag)";

    private final static String QUERY_SQL = "SELECT * FROM tag WHERE tag_json = :tagJson";

    public static final BiFunction<Row, RowMetadata, TagEO> MAPPING_FUNCTION = (row, rowMetaData) -> TagEO.builder()
            .id(row.get("id", Integer.class))
            .tagJson(row.get("tag_json", String.class))
            .build();

    public Mono<Integer> save(String tag) {
        return this.databaseClient.sql(CREATE_SQL)
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                .bind("tag", tag)
                .fetch()
                .first()
                .map(r -> (Integer) r.get("id"))
                .doOnNext(id -> log.info("create tag id is: [{}], name is: [{}].", id, tag));
    }

    public Mono<TagEO> findByTag(String tag) {
        return databaseClient.sql(QUERY_SQL)
                .bind("tagJson", tag)
                .map(MAPPING_FUNCTION)
                .one();
    }
}
