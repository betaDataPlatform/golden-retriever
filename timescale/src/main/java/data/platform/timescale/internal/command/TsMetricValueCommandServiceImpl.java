package data.platform.timescale.internal.command;

import data.platform.common.domain.MetricValue;
import data.platform.common.service.command.MetricTagCommandService;
import data.platform.common.service.command.MetricValueCommandService;
import data.platform.common.util.DateUtil;
import data.platform.timescale.internal.cache.TsCacheService;
import data.platform.timescale.persistence.mapping.DataPointEO;
import data.platform.timescale.persistence.repository.TsDataPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@ConditionalOnBean(name = "timeScaleConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class TsMetricValueCommandServiceImpl implements MetricValueCommandService {

    final MetricTagCommandService metricTagCommandService;

    final TsCacheService tsCacheService;

    final TsDataPointRepository dataPointRepository;

    @Override
    public Mono<Long> save(MetricValue metricValue) {
        return Mono.just(metricValue)
                // 数据加入缓存
                .flatMap(mv -> metricTagCommandService.save(mv))
                // 转换成dataPoint
                .map(count -> metricValueToDataPoint(metricValue))
                .filter(dataPointOptional -> dataPointOptional.isPresent())
                .map(dataPointOptional -> dataPointOptional.get())
                // 插入数据库
                .flatMap(dataPoint -> dataPointRepository.save(dataPoint))
                .map(eo -> 1L);
    }

    private Optional<DataPointEO> metricValueToDataPoint(MetricValue metricValue) {
        try {
            DataPointEO eo = new DataPointEO();
            eo.setEventTime(DateUtil.getDateOfLocalDateTime(metricValue.getEventTime()));

            Optional<Integer> metricIdOptional = tsCacheService.getMetricId(metricValue.getMetric());
            if (!metricIdOptional.isPresent()) {
                log.error("metric: [{}] can't find in cache!", metricValue.getMetric());
                return Optional.empty();
            }
            eo.setMetricId(metricIdOptional.get());

            String tagJson = metricValue.getTagJson();
            Optional<Integer> tagIdOptional = tsCacheService.getTagId(tagJson);
            if (!tagIdOptional.isPresent()) {
                log.error("tag: [{}] can't find in cache!", tagJson);
                return Optional.empty();
            }
            eo.setTagId(tagIdOptional.get());

            eo.setValue(metricValue.getValue());
            return Optional.of(eo);
        } catch (Exception ex) {
            log.error("", ex);
            return Optional.empty();
        }
    }
}
