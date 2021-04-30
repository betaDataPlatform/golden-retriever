package data.platform.cassandra.internal.command;

import data.platform.cassandra.infra.persistence.repository.CassandraMetricTagRepository;
import data.platform.cassandra.internal.cache.CassandraCacheService;
import data.platform.common.domain.MetricValue;
import data.platform.common.service.command.MetricTagCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@ConditionalOnBean(name = "cassandraConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class CassandraMetricTagCommandServiceImpl implements MetricTagCommandService {

    final CassandraMetricTagRepository cassandraMetricTagRepository;

    final CassandraCacheService cassandraCacheService;

    @Override
    public Mono<Integer> saveAll(List<MetricValue> metricValues) {
        return Flux.fromIterable(metricValues)
                .flatMap(metricValue -> getMetricTag(metricValue))
                .collectList()
                .flatMap(metricTags -> cassandraCacheService.metricTagPutCache(metricTags));
    }
}
