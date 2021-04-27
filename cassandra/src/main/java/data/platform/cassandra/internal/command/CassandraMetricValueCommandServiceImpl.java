package data.platform.cassandra.internal.command;

import data.platform.cassandra.infra.persistence.mapping.DataPointEO;
import data.platform.cassandra.infra.persistence.mapping.DataPointKey;
import data.platform.cassandra.infra.persistence.repository.CassandraDataPointRepository;
import data.platform.cassandra.infra.persistence.repository.CassandraMetricTagRepository;
import data.platform.common.domain.MetricValue;
import data.platform.common.service.command.MetricTagCommandService;
import data.platform.common.service.command.MetricValueCommandService;
import data.platform.common.service.query.MetricTagQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@ConditionalOnBean(name = "cassandraConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class CassandraMetricValueCommandServiceImpl implements MetricValueCommandService {

    final CassandraDataPointRepository cassandraDataPointRepository;

    final CassandraMetricTagRepository cassandraMetricTagRepository;

    final MetricTagQueryService metricTagQueryService;

    final MetricTagCommandService metricTagCommandService;

    private DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
                .filter(dataPoint -> Objects.nonNull(dataPoint.getTtl()))
                .flatMap(dataPoint -> {
                    log.info(dataPoint.toString());
                    if (Objects.nonNull(dataPoint.getTtl())) {
                        return cassandraDataPointRepository.insert(dataPoint, dataPoint.getTtl());
                    } else {
                        return cassandraDataPointRepository.insert(dataPoint, 0);
                    }
                })
                .map(eo -> 1L);
    }

    private Optional<DataPointEO> metricValueToDataPoint(MetricValue metricValue) {
        try {
            DataPointKey dataPointKey = new DataPointKey();
            dataPointKey.setMetric(metricValue.getMetric());
            dataPointKey.setTagJson(metricValue.getTagJson());

            dataPointKey.setPartition(metricValue.getEventTime().toLocalDate());
            dataPointKey.setOffset(metricValue.getEventTime().toLocalTime());

            DataPointEO dataPoint = new DataPointEO();
            dataPoint.setDataPointKey(dataPointKey);
            dataPoint.setValue(metricValue.getValue());
            dataPoint.setTtl(metricValue.getTtl());

            return Optional.of(dataPoint);
        } catch (Exception ex) {
            log.error("", ex);
            return Optional.empty();
        }
    }
}
