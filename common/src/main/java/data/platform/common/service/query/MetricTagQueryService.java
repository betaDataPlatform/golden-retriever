package data.platform.common.service.query;

import reactor.core.publisher.Flux;

public interface MetricTagQueryService {

    Flux<String> filterMetric(String metric);

    Flux<String> filterTagKeyOfMetric(String metric);

    Flux<String> filterTagValueOfMetric(String metric, String tagKey, String tagValue);

}
