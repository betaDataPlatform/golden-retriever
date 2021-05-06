package data.platform.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.platform.common.domain.MetricValue;
import data.platform.common.event.MetricValueEvent;
import data.platform.common.query.QueryBuilder;
import data.platform.common.response.QueryResults;
import data.platform.common.service.query.MetricResultQueryService;
import data.platform.common.util.DateUtil;
import data.platform.rest.domain.Metric;
import data.platform.rest.domain.Response;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class ApiController {

    final ApplicationContext applicationContext;

    final MetricResultQueryService metricResultQueryService;

    final MeterRegistry dataSetMeterRegistry;

    private Timer queryTimer;

    @Value("${query.log.executeTime}")
    private int executeTimeLog;

    @PostConstruct
    public void init() {
        queryTimer = Timer.builder("dp.query.timer")
                .publishPercentiles(0.95, 0.99) // 95th percentile
                .publishPercentileHistogram()
                .register(dataSetMeterRegistry);
    }

    @PostMapping("/datapoints")
    public Mono<ResponseEntity<Response>> putData(@RequestBody List<Metric> metrics) {
        // metric --> metricValue
        // 把接收的数据,处理成list
        return Flux.fromIterable(metrics)
                .flatMap(metric -> {
                    Set<MetricValue> metricValueSet = metric.getSamplePoints().stream()
                            .map(samplePoint -> MetricValue.builder()
                                    .metric(metric.getName())
                                    .tag(metric.getTags())
                                    .eventTime(DateUtil.getDateTimeOfTimestamp(Long.parseLong(samplePoint[0].toString())))
                                    .value(Double.parseDouble(samplePoint[1].toString()))
                                    .build())
                            .collect(Collectors.toSet());
                    return Flux.fromIterable(metricValueSet);
                })
                .doOnNext(metricValue -> applicationContext.publishEvent(new MetricValueEvent(metricValue)))
                .count()
                .map(dataPointNum -> ResponseEntity.ok()
                        .body(new Response()));
    }

    @PostMapping(value = "/datapoints/query")
    public Mono<ResponseEntity<QueryResults>> query(@RequestBody QueryBuilder queryBuilder) {
        return metricResultQueryService.query(queryBuilder)
                .elapsed()
                .doOnNext(tuple2 -> {
                    queryTimer.record(tuple2.getT1(), TimeUnit.MILLISECONDS);
                    if (tuple2.getT1() > executeTimeLog) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            log.info("query time: " + tuple2.getT1() + "," + objectMapper.writeValueAsString(queryBuilder));
                        } catch (JsonProcessingException ex) {
                            log.error("", ex);
                        }
                    }
                })
                .map(tuple2 -> ResponseEntity.ok()
                        .header("executetime", tuple2.getT1().toString())
                        .body(tuple2.getT2()));
    }
}
