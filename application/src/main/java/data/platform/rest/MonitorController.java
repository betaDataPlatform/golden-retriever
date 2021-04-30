package data.platform.rest;

import data.platform.common.event.MonitorEvent;
import data.platform.common.util.DateUtil;
import data.platform.rest.domain.Metric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@Slf4j
public class MonitorController {

    private List<Metric> metrics = new ArrayList<>();

    @GetMapping("/monitor")
    public Mono<List<Metric>> monitor() {
        return Mono.just(metrics);
    }

    @EventListener
    public void onSseEvent(MonitorEvent monitorEvent) {
         metrics = monitorEvent.getMetricValues().stream()
                 .map(metricValue -> {
                     Metric metric = new Metric();
                     metric.setName(metricValue.getMetric());
                     metric.setTags(metricValue.getTag());

                     List<Object[]> samplePoints = new ArrayList<>(1);
                     samplePoints.add(new Object[]{DateUtil.getDateOfLocalDateTime(metricValue.getEventTime()).getTime(), metricValue.getValue()});
                     metric.setSamplePoints(samplePoints);
                     return metric;
                 })
                 .collect(Collectors.toList());
    }
}
