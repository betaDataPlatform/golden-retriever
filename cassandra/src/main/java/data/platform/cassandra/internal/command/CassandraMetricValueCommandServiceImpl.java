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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Mono<Integer> saveAll(List<MetricValue> metricValueList) {
        return metricTagCommandService.saveAll(metricValueList)
                .then(cassandraDataPointRepository
                        .insertAll(createDataPointEOs(metricValueList), 0)
                        .count()
                        .map(count -> count.intValue()));
    }

    private List<DataPointEO> createDataPointEOs(List<MetricValue> metricValueList) {
        return metricValueList.stream()
                .map(metricValue -> metricValueToDataPoint(metricValue))
                .filter(dataPointOptional -> dataPointOptional.isPresent())
                .map(dataPointOptional -> dataPointOptional.get())
                .collect(Collectors.toList());
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

            return Optional.of(dataPoint);
        } catch (Exception ex) {
            log.error("", ex);
            return Optional.empty();
        }
    }
}
