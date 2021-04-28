package data.platform.cassandra.internal.query;

import data.platform.cassandra.internal.cache.CassandraCacheService;
import data.platform.common.domain.MetricTag;
import data.platform.common.service.query.MetricTagQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@ConditionalOnBean(name = "cassandraConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class CassandraMetricTagQueryServiceImpl implements MetricTagQueryService {

    final CassandraCacheService cassandraCacheService;

    @Override
    public Flux<String> filterMetric(String metric) {
        return null;
    }

    @Override
    public Flux<String> filterTagKeyOfMetric(String metric) {
        return null;
    }

    @Override
    public Flux<String> filterTagValueOfMetric(String metric, String tagKey, String tagValue) {
        return null;
    }

    @Override
    public Flux<MetricTag> findMetricTag(String metric, String tagName, String tagValue) {
        return Flux.fromIterable(cassandraCacheService.findMetricTag(metric, tagName, tagValue));
    }

}
