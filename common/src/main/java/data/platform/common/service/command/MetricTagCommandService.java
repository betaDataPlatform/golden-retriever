package data.platform.common.service.command;

import data.platform.common.domain.MetricTag;
import data.platform.common.domain.MetricValue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public interface MetricTagCommandService {

    Mono<Long> save(MetricValue metricValue);

    Mono<Integer> saveAll(List<MetricValue> metricValues);

    default Flux<MetricTag> getMetricTag(MetricValue metricValue) {
        return Mono.just(metricValue)
                .map(mv -> getMetricTags(mv))
                .flatMapMany(Flux::fromIterable);
    }

    default List<MetricTag> getMetricTags(MetricValue metricValue) {
        return metricValue.getTag().entrySet().stream()
                .map(entry -> {
                    MetricTag metricTag = new MetricTag();
                    metricTag.setMetric(metricValue.getMetric());
                    metricTag.setTagName(entry.getKey());
                    metricTag.setTagValue(entry.getValue());
                    metricTag.setTag(metricValue.getTagJson());
                    return metricTag;
                })
                .collect(Collectors.toList());
    }
}
