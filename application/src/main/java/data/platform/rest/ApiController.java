package data.platform.rest;

import data.platform.common.domain.MetricValue;
import data.platform.common.event.MetricValueEvent;
import data.platform.common.util.DateUtil;
import data.platform.rest.domain.Metric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class ApiController {

    final ApplicationContext applicationContext;

    @RequestMapping("/datapoints")
    @ResponseBody
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
                metricValue.setTags(metric.getTags());
                metricValue.setTtl(metric.getTtl());
                // 发布事件
                applicationContext.publishEvent(new MetricValueEvent(metricValue));
            }
        }
        return Mono.just(true);
    }
}
