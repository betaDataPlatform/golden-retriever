package data.platform.timescale.internal.query;

import data.platform.common.service.query.MetricTagQueryService;
import data.platform.timescale.internal.cache.TsCacheService;
import data.platform.timescale.persistence.repository.TsMetricTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.Set;
import java.util.stream.Collectors;

@ConditionalOnBean(name = "timeScaleConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class TsMetricTagQueryServiceImpl implements MetricTagQueryService {

    final TsCacheService tsCacheService;

    final TsMetricTagRepository metricTagRepository;

    @Override
    public Flux<String> filterMetric(String metric) {
        return Flux.fromIterable(tsCacheService.getAllMetrics())
                .filter(m -> m.toLowerCase().contains(metric));
    }

    @Override
    public Flux<String> filterTagKeyOfMetric(String metric) {
        Set<String> tagKeys = tsCacheService.getAllMetricTags()
                .stream()
                .filter(metricTag -> metricTag.getMetric().equals(metric))
                .map(metricTag -> metricTag.getTagName())
                .collect(Collectors.toSet());
        return Flux.fromIterable(tagKeys);
    }

    @Override
    public Flux<String> filterTagValueOfMetric(String metric, String tagKey, String tagValue) {
        Set<String> tagKeys = tsCacheService.getAllMetricTags()
                .stream()
                .filter(metricTag -> metricTag.getMetric().equals(metric) && metricTag.getTagName().equals(tagKey))
                .filter(metricTag -> !StringUtils.hasLength(tagValue) || metricTag.getTagValue().toLowerCase().contains(tagValue))
                .map(metricTag -> metricTag.getTagValue())
                .collect(Collectors.toSet());
        return Flux.fromIterable(tagKeys);
    }

}
