package data.platform.cassandra.infra.persistence.repository;

import data.platform.cassandra.infra.persistence.mapping.DataPointEO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@ConditionalOnBean(name = "cassandraConfig")
@Repository
@AllArgsConstructor
@Slf4j
public class CassandraDataPointRepository {

    final ReactiveCassandraOperations reactiveCassandraOperations;

    private static InsertOptions INSERT_NULLS = InsertOptions.builder().withInsertNulls().build();

    public Mono<DataPointEO> insert(DataPointEO entity, Integer ttl) {
        Assert.notNull(entity, "Entity must not be null");
        if (ttl > 0) {
            InsertOptions insertOptions = InsertOptions.builder().ttl(ttl).build();
            return reactiveCassandraOperations.insert(entity, insertOptions).thenReturn(entity);
        } else {
            return reactiveCassandraOperations.insert(entity, INSERT_NULLS).thenReturn(entity);
        }
    }

    public Flux<DataPointEO> insertAll(List<DataPointEO> entities, Integer ttl) {
        return Flux.fromIterable(entities).flatMap(entity -> insert(entity, ttl));
    }

    public Flux<DataPointEO> findBySql(Flux<String> sqlFlux) {
        return sqlFlux.flatMap(sql -> reactiveCassandraOperations.select(sql, DataPointEO.class));
    }

    public Flux<DataPointEO> find(Flux<GroupedFlux<Integer, String>> groups) {
        return groups.flatMap(groupFlux -> findBySql(groupFlux))
                .filter(eo -> Objects.nonNull(eo.getDataPointKey().getMetric()));
    }

}
