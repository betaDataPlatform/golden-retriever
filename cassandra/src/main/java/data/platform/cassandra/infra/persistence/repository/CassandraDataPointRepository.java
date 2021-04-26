package data.platform.cassandra.infra.persistence.repository;

import data.platform.cassandra.infra.persistence.mapping.DataPointEO;
import data.platform.common.query.QueryAggregatorUnit;
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

import java.time.LocalDate;
import java.time.LocalTime;
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

    public Flux<DataPointEO> findBySql(Flux<String> sqlFlux) {
        return sqlFlux.flatMap(sql -> reactiveCassandraOperations.select(sql, DataPointEO.class));
    }

    public Flux<DataPointEO> find(Flux<GroupedFlux<Integer, String>> groups) {
        return groups.flatMap(groupFlux -> findBySql(groupFlux));
    }

    public Flux<DataPointEO> statFunction(QueryAggregatorUnit aggregatorUnit, Flux<GroupedFlux<Integer, String>> groups, LocalDate partition, LocalTime offset) {
        return find(groups)
                .filter(eo -> Objects.nonNull(eo.getDataPointKey().getMetric()))
                .collectList()
                .filter(eos -> eos.size() > 0)
                .map(eos -> {
                    DataPointEO eo = new DataPointEO();
                    eo.setDataPointKey(eos.get(0).getDataPointKey());
                    eo.getDataPointKey().setPartition(partition);
                    eo.getDataPointKey().setOffset(offset);

                    double value = 0.0;
                    if (aggregatorUnit == QueryAggregatorUnit.AVG) {
                        value = eos.stream().mapToDouble(DataPointEO::getValue).average().getAsDouble();
                    } else if (aggregatorUnit == QueryAggregatorUnit.MAX) {
                        value = eos.stream().mapToDouble(DataPointEO::getValue).max().getAsDouble();
                    } else if (aggregatorUnit == QueryAggregatorUnit.MIN) {
                        value = eos.stream().mapToDouble(DataPointEO::getValue).min().getAsDouble();
                    } else if (aggregatorUnit == QueryAggregatorUnit.SUM) {
                        value = eos.stream().mapToDouble(DataPointEO::getValue).sum();
                    }
                    eo.setValue(value);
                    return eo;
                })
                .flux();

    }

}
