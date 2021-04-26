package data.platform.common.service.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.platform.common.domain.MetricValue;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public interface MetricValueCommandService {

    Mono<Long> save(MetricValue metricValue);

    default Optional<String> getTag(MetricValue metricValue) {
        try {
            Map<String, String> sortedTag = new TreeMap<>();
            sortedTag.putAll(metricValue.getTags());
            ObjectMapper mapper = new ObjectMapper();
            String tag = mapper.writeValueAsString(sortedTag);
            return Optional.of(tag);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
