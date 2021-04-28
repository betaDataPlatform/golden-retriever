package data.platform.rest;

import data.platform.common.domain.MetricValue;
import data.platform.common.event.MetricValueEvent;
import data.platform.common.query.QueryBuilder;
import data.platform.common.response.QueryResults;
import data.platform.common.service.query.MetricResultQueryService;
import data.platform.common.util.DateUtil;
import data.platform.rest.domain.Metric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class ApiController {

    final ApplicationContext applicationContext;

    final MetricResultQueryService metricResultQueryService;

    @PostMapping("/datapoints")
    public Mono<Boolean> putData(@RequestBody List<Metric> metrics) {
        // metric --> metricValue
        // 把接收的数据,处理成list
        List<MetricValue> metricValues = new ArrayList<>();
        for (Metric metric : metrics) {
            for (Object[] samplePoint : metric.getSamplePoints()) {
                MetricValue metricValue = new MetricValue();
                metricValue.setEventTime(DateUtil.getDateTimeOfTimestamp(Long.parseLong(samplePoint[0].toString())));
                metricValue.setValue(Double.parseDouble(samplePoint[1].toString()));
                metricValue.setMetric(metric.getName());
                metricValue.setTag(metric.getTags());
                metricValue.setTtl(metric.getTtl());
                // 发布事件
                applicationContext.publishEvent(new MetricValueEvent(metricValue));
            }
        }
        return Mono.just(true);
    }

    @PostMapping(value = "/datapoints/query")
    public Mono<ResponseEntity<QueryResults>> query(@RequestBody QueryBuilder queryBuilder) {
        return metricResultQueryService.query(queryBuilder)
                .elapsed()
                .map(tuple2 -> ResponseEntity.ok()
                        .header("executetime", tuple2.getT1().toString())
                        .body(tuple2.getT2()));
    }
}
