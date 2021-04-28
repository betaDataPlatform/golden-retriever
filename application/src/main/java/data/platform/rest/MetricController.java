package data.platform.rest;

import data.platform.common.service.query.MetricTagQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/metricTag")
@Slf4j
@RequiredArgsConstructor
public class MetricController {

    final MetricTagQueryService metricTagQueryService;

    @PostMapping(value = "/queryMetric", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<List<String>> queryMetric(@RequestBody Map<String, String> metricMap) {
        return metricTagQueryService.filterMetric(metricMap.get("metric")).collectList();
    }

    @PostMapping(value = "/queryTagKeyOfMetric", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<List<String>> queryTagKeyOfMetric(@RequestBody Map<String, String> metricMap) {
        return metricTagQueryService.filterTagKeyOfMetric(metricMap.get("metric")).collectList();
    }

    @PostMapping(value = "/queryTagValueOfMetric", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<List<String>> queryTagValueOfMetric(@RequestBody Map<String, String> metricMap) {
        return metricTagQueryService.filterTagValueOfMetric(metricMap.get("metric"),
                metricMap.get("tagKey"), metricMap.get("tagValue")).collectList();
    }
}
