package data.platform.common.service.query;

import data.platform.common.domain.MetricTag;
import reactor.core.publisher.Flux;

public interface MetricTagQueryService {

    Flux<MetricTag> findMetricTag(String metric, String tagName, String tagValue);
}
