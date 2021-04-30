package data.platform.monitor.domain;

import data.platform.common.domain.MetricValue;
import data.platform.common.event.MetricValueEvent;
import data.platform.common.event.MonitorEvent;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.core.instrument.step.StepMeterRegistry;
import io.micrometer.core.instrument.util.DoubleFormat;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import io.micrometer.core.instrument.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class DataSetMeterRegistry extends StepMeterRegistry {

    DataSetRegistryConfig config;

    ApplicationEventPublisher applicationEventPublisher;

    static final Integer TTL = 7 * 24 * 60 * 60;

    static final long MEGABYTE = 1024L * 1024L;

    // metricsName -> tagKey -> tagValue
    static Map<String, Map<String, String>> monitorMetrics;

    static Map<String, String> metricsConvert;

    ThreadLocal<DecimalFormat> INTEGER_FORMAT = ThreadLocal.withInitial(() -> {
        // the following will ensure a dot ('.') as decimal separator
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.CHINA);
        return new DecimalFormat("#", otherSymbols);
    });

    ThreadLocal<DecimalFormat> TWO_DECIMAL_FORMAT = ThreadLocal.withInitial(() -> {
        // the following will ensure a dot ('.') as decimal separator
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.CHINA);
        return new DecimalFormat("#.##", otherSymbols);
    });

    static {
        monitorMetrics = new HashMap<>();
        metricsConvert = new HashMap<>();

        monitorMetrics.put("jvm.memory.used", new HashMap<>());
        metricsConvert.put("jvm.memory.used", "bytesToMeg");

        monitorMetrics.put("process.cpu.usage", new HashMap<>());
        metricsConvert.put("process.cpu.usage", "decimalMulti100");

        monitorMetrics.put("system.cpu.usage", new HashMap<>());
        metricsConvert.put("system.cpu.usage", "decimalMulti100");

        monitorMetrics.put("jvm.threads.daemon", new HashMap<>());

        monitorMetrics.put("jvm.threads.live", new HashMap<>());

        monitorMetrics.put("cassandra.cpu.usage", new HashMap<>());
        metricsConvert.put("cassandra.cpu.usage", "decimalMulti100");

        monitorMetrics.put("dp.metricValue.count.save", new HashMap<>());
        monitorMetrics.put("dp.metricValue.count.drop", new HashMap<>());

        monitorMetrics.put("dp.query.timer.count", new HashMap<>());
        monitorMetrics.put("dp.query.timer.max", new HashMap<>());
        monitorMetrics.put("dp.query.timer.avg", new HashMap<>());
        metricsConvert.put("dp.query.timer.avg", "integer");
        monitorMetrics.put("dp.query.timer.percentile", new HashMap<>());
        metricsConvert.put("dp.query.timer.percentile", "integer");
    }

    public DataSetMeterRegistry(DataSetRegistryConfig config, Clock clock, ApplicationEventPublisher applicationEventPublisher) {
        super(config, clock);
        this.config = config;
        this.applicationEventPublisher = applicationEventPublisher;
        config().namingConvention(NamingConvention.dot);
        start(new NamedThreadFactory("golden-retriever-metrics-publisher"));
    }

    @Override
    public void start(ThreadFactory threadFactory) {
        if (this.config.enabled()) {
            log.info("publishing metrics to logs every " + TimeUtils.format(this.config.step()));
        } else {
            log.info("publishing metrics is disabled.");
        }
        super.start(threadFactory);
    }

    @Override
    protected void publish() {
        if (config.enabled()) {
            List<MetricValue> metricValues = getMeters().stream()
                    .sorted((m1, m2) -> {
                        int typeComp = m1.getId().getType().compareTo(m2.getId().getType());
                        if (typeComp == 0) {
                            return m1.getId().getName().compareTo(m2.getId().getName());
                        }
                        return typeComp;
                    })
                    .flatMap(meter -> meter.match(
                            this::writeGauge,
                            this::writeCounter,
                            this::writeTimer,
                            this::writeSummary,
                            this::writeLongTaskTimer,
                            this::writeTimeGauge,
                            this::writeFunctionCounter,
                            this::writeFunctionTimer,
                            this::writeCustomMetric
                    ))
                    .filter(metricValue -> {
                        if (monitorMetrics.containsKey(metricValue.getMetric())) {
                            Map<String, String> tagMap = monitorMetrics.get(metricValue.getMetric());
                            if (tagMap.isEmpty()) {
                                covertValue(metricValue);
                                return true;
                            }
                            for (Map.Entry<String, String> ite : metricValue.getTag().entrySet()) {
                                if (tagMap.containsKey(ite.getKey()) && tagMap.get(ite.getKey()).equals(ite.getValue())) {
                                    covertValue(metricValue);
                                    return true;
                                }
                            }
                        }
                        return false;
                    })
                    .peek(metricValue -> applicationEventPublisher.publishEvent(new MetricValueEvent(metricValue)))
                    .collect(Collectors.toList());

            // 发送监控指标值，前端展现
            MonitorEvent monitorEvent = new MonitorEvent(metricValues);
            applicationEventPublisher.publishEvent(monitorEvent);
        }
    }

    @Override
    protected TimeUnit getBaseTimeUnit() {
        return TimeUnit.MILLISECONDS;
    }

    Stream<MetricValue> writeGauge(Gauge gauge) {
        double value = gauge.value();
        if (Double.isFinite(value)) {
            return Stream.of(writeDataPoint(gauge.getId(), config().clock().wallTime(), value));
        }
        return Stream.empty();
    }

    Stream<MetricValue> writeCounter(Counter counter) {
        return Stream.of(writeDataPoint(counter.getId(), config().clock().wallTime(), counter.count()));
    }

    Stream<MetricValue> writeTimer(Timer timer) {
        long wallTime = config().clock().wallTime();
        return Stream.of(
                writeDataPoint(idWithSuffix(timer.getId(), "count"), wallTime, timer.count()),
                writeDataPoint(idWithSuffix(timer.getId(), "max"), wallTime, timer.max(getBaseTimeUnit())),
                writeDataPoint(idWithSuffix(timer.getId(), "avg"), wallTime, timer.mean(getBaseTimeUnit())),
                writeDataPoint(idWithSuffix(timer.getId(), "sum"), wallTime, timer.totalTime(getBaseTimeUnit()))
        );
    }

    Stream<MetricValue> writeSummary(DistributionSummary summary) {
        long wallTime = config().clock().wallTime();
        return Stream.of(
                writeDataPoint(idWithSuffix(summary.getId(), "count"), wallTime, summary.count()),
                writeDataPoint(idWithSuffix(summary.getId(), "avg"), wallTime, summary.mean()),
                writeDataPoint(idWithSuffix(summary.getId(), "sum"), wallTime, summary.totalAmount()),
                writeDataPoint(idWithSuffix(summary.getId(), "max"), wallTime, summary.max())
        );
    }

    Stream<MetricValue> writeLongTaskTimer(LongTaskTimer timer) {
        long wallTime = config().clock().wallTime();
        return Stream.of(
                writeDataPoint(idWithSuffix(timer.getId(), "activeTasks"), wallTime, timer.activeTasks()),
                writeDataPoint(idWithSuffix(timer.getId(), "duration"), wallTime, timer.duration(getBaseTimeUnit()))
        );
    }

    Stream<MetricValue> writeTimeGauge(TimeGauge timeGauge) {
        double value = timeGauge.value(getBaseTimeUnit());
        if (Double.isFinite(value)) {
            return Stream.of(writeDataPoint(timeGauge.getId(), config().clock().wallTime(), value));
        }
        return Stream.empty();
    }

    Stream<MetricValue> writeFunctionCounter(FunctionCounter counter) {
        double count = counter.count();
        if (Double.isFinite(count)) {
            return Stream.of(writeDataPoint(counter.getId(), config().clock().wallTime(), count));
        }
        return Stream.empty();
    }

    Stream<MetricValue> writeFunctionTimer(FunctionTimer timer) {
        long wallTime = config().clock().wallTime();
        return Stream.of(
                writeDataPoint(idWithSuffix(timer.getId(), "count"), wallTime, timer.count()),
                writeDataPoint(idWithSuffix(timer.getId(), "avg"), wallTime, timer.mean(getBaseTimeUnit())),
                writeDataPoint(idWithSuffix(timer.getId(), "sum"), wallTime, timer.totalTime(getBaseTimeUnit()))
        );
    }

    Stream<MetricValue> writeCustomMetric(Meter meter) {
        long wallTime = config().clock().wallTime();
        List<Tag> meterTags = getConventionTags(meter.getId());
        Map<String, String> tag = new HashMap<>();
        for (Tag meterTag : meterTags) {
            tag.put(meterTag.getKey(), meterTag.getValue());
        }
        List<MetricValue> metricValues = new ArrayList<>();
        for (Measurement measurement : meter.measure()) {
            double value = measurement.getValue();
            if (!Double.isFinite(value)) {
                continue;
            }
            String metricName = measurement.getStatistic().getTagValueRepresentation();

            LocalDateTime eventTime = dateToLocalDateTime(wallTime);

            MetricValue metricValue = new MetricValue();
            metricValue.setMetric(metricName);
            metricValue.setTag(tag);
            metricValue.setEventTime(eventTime);
            metricValue.setValue(Double.valueOf(DoubleFormat.wholeOrDecimal(value)));

            metricValues.add(metricValue);
        }
        return metricValues.stream();
    }

    MetricValue writeDataPoint(Meter.Id id, long wallTime, double value) {
        String metricName = getConventionName(id);

        LocalDateTime eventTime = dateToLocalDateTime(wallTime);

        Map<String, String> tag = new HashMap<>();
        List<Tag> meterTags = getConventionTags(id);
        for (Tag meterTag : meterTags) {
            tag.put(meterTag.getKey(), meterTag.getValue());
        }

        MetricValue metricValue = new MetricValue();
        metricValue.setMetric(metricName);
        metricValue.setTag(tag);
        metricValue.setEventTime(eventTime);
        metricValue.setValue(Double.valueOf(DoubleFormat.wholeOrDecimal(value)));
        return metricValue;
    }

    private Meter.Id idWithSuffix(Meter.Id id, String suffix) {
        return id.withName(id.getName() + "." + suffix);
    }

    private LocalDateTime dateToLocalDateTime(long time) {
        Date date = new Date(time);
        LocalDateTime ldt = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return ldt;
    }

    private void covertValue(MetricValue metricValue) {
        if (metricsConvert.containsKey(metricValue.getMetric())) {
            String convert = metricsConvert.get(metricValue.getMetric());
            if (convert.equals("bytesToMeg")) {
                metricValue.setValue(Double.valueOf(bytesToMeg(metricValue.getValue())));
            } else if (convert.equals("decimalMulti100")) {
                metricValue.setValue(Double.valueOf(decimalMulti100(metricValue.getValue())));
            } else if (convert.equals("integer")) {
                metricValue.setValue(Double.valueOf(integerFormat(metricValue.getValue())));
            }
        }
    }

    private String bytesToMeg(double bytes) {
        return INTEGER_FORMAT.get().format(bytes / MEGABYTE);
    }

    private String decimalMulti100(double values) {
        return TWO_DECIMAL_FORMAT.get().format(values * 100);
    }

    private String integerFormat(double values) {
        return INTEGER_FORMAT.get().format(values);
    }

}
