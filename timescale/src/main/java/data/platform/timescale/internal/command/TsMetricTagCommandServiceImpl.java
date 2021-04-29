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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
        return null;
    }

    @Override
    public Mono<Integer> saveAll(List<MetricValue> metricValues) {
        return Flux.fromIterable(metricValues)
                .flatMap(metricValue -> getMetricTag(metricValue))
                .collectList()
                .flatMap(metricTags -> tsCacheService.metricPutCache(metricTags)
                        .then(tsCacheService.tagPutCache(metricTags))
                        .then(tsCacheService.metricTagPutCache(metricTags)));
    }
}
