package data.platform.common.service.query;

import data.platform.common.domain.MetricTag;
import reactor.core.publisher.Flux;

public interface MetricTagQueryService {

    Flux<String> filterMetric(String metric);

    Flux<String> filterTagKeyOfMetric(String metric);

    Flux<String> filterTagValueOfMetric(String metric, String tagKey);

    Flux<MetricTag> findMetricTag(String metric, String tagName, String tagValue);
}
