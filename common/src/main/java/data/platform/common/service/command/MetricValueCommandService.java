package data.platform.common.service.command;

import data.platform.common.domain.MetricValue;
import reactor.core.publisher.Mono;

public interface MetricValueCommandService {

    Mono<Long> save(MetricValue metricValue);
}
