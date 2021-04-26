package data.platform.common.service.command;

import data.platform.common.domain.MetricValue;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.TreeMap;

public interface MetricValueCommandService {

    Mono<Long> save(MetricValue metricValue);

    default Map<String, String> getSortedTag(MetricValue metricValue) {
        Map<String, String> sortedTag = new TreeMap<>();
        sortedTag.putAll(metricValue.getTags());
        return sortedTag;
    }
}
