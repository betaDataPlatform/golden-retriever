package data.platform.cassandra.infra.persistence.repository;

import data.platform.cassandra.infra.persistence.mapping.MetricTagEO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ConditionalOnBean(name = "cassandraConfig")
@Repository
@AllArgsConstructor
@Slf4j
public class CassandraMetricTagRepository {

    final ReactiveCassandraOperations reactiveCassandraOperations;

    public Flux<MetricTagEO> findAll() {
        return reactiveCassandraOperations.select(Query.empty(), MetricTagEO.class);
    }

    public Mono<MetricTagEO> save(MetricTagEO entity) {
        return reactiveCassandraOperations.insert(entity).thenReturn(entity);
    }
}
