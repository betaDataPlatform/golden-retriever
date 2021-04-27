package data.platform.cassandra.internal.command;

import data.platform.cassandra.infra.persistence.repository.CassandraMetricTagRepository;
import data.platform.cassandra.internal.cache.CassandraCacheService;
import data.platform.common.domain.MetricValue;
import data.platform.common.service.command.MetricTagCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@ConditionalOnBean(name = "cassandraConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class CassandraMetricTagCommandServiceImpl implements MetricTagCommandService {

    final CassandraMetricTagRepository cassandraMetricTagRepository;

    final CassandraCacheService cassandraCacheService;

    @Override
    public Mono<Long> save(MetricValue metricValue) {
        return getMetricTag(metricValue)
                .flatMap(metricTag -> cassandraCacheService.metricTagPutCache(metricTag))
                .count();
    }
}
