package data.platform.common.service.command;

import data.platform.common.domain.MetricValue;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MetricValueCommandService {

    Mono<Long> save(MetricValue metricValue);

    Mono<Integer> saveAll(List<MetricValue> metricValueList);
}
