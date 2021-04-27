package data.platform.timescale.internal.command;

import data.platform.common.domain.MetricValue;
import data.platform.common.service.command.MetricTagCommandService;
import data.platform.timescale.internal.cache.TsCacheService;
import data.platform.timescale.persistence.repository.TsMetricTagRepository;
import data.platform.timescale.persistence.repository.TsTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@ConditionalOnBean(name = "timeScaleConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class TsMetricTagCommandServiceImpl implements MetricTagCommandService {

    final TsTagRepository tagRepository;

    final TsMetricTagRepository metricTagRepository;

    final TsCacheService tsCacheService;

    @Override
    public Mono<Long> save(MetricValue metricValue) {
        // convert to: metric:tagKey:tagValue:tag
        return getMetricTag(metricValue)
                .flatMap(metricTag -> tsCacheService.metricPutCache(metricTag.getMetric())
                                .then(tsCacheService.tagPutCache(metricTag.getTag()))
                                .then(tsCacheService.metricTagPutCache(metricTag))
                )
                .count();
    }

}
