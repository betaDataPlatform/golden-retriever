package data.platform.timescale.internal.query;

import data.platform.common.domain.MetricTag;
import data.platform.common.service.query.MetricTagQueryService;
import data.platform.timescale.internal.cache.TsCacheService;
import data.platform.timescale.persistence.repository.TsMetricTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@ConditionalOnBean(name = "timeScaleConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class TsMetricTagQueryServiceImpl implements MetricTagQueryService {

    final TsCacheService tsCacheService;

    final TsMetricTagRepository metricTagRepository;

    @Override
    public Flux<MetricTag> findMetricTag(String metric, String tagName, String tagValue) {
        return Flux.empty();
    }
}
