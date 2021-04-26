package data.platform.common.service.command;

import data.platform.common.domain.MetricTag;
import data.platform.common.domain.MetricValue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

public interface MetricTagCommandService {

    Mono<Long> save(MetricValue metricValue, String tag);

    default Flux<MetricTag> getMetricTag(MetricValue metricValue, String tag) {
        return Mono.just(metricValue)
                .map(mv -> getMetricTags(mv, tag))
                .flatMapMany(Flux::fromIterable);
    }

    default List<MetricTag> getMetricTags(MetricValue metricValue, String tag) {
        return metricValue.getTags().entrySet().stream()
                .map(entry -> {
                    MetricTag metricTag = new MetricTag();
                    metricTag.setMetric(metricValue.getMetric());
                    metricTag.setTagName(entry.getKey());
                    metricTag.setTagValue(entry.getValue());
                    metricTag.setTag(tag);
                    return metricTag;
                })
                .collect(Collectors.toList());
    }
}
