package data.platform.common.event;

import data.platform.common.domain.MetricValue;
import data.platform.common.protobuf.MetricValueProto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.ZoneId;

@Getter
public class DropMetricValueEvent extends ApplicationEvent {

    private MetricValue metricValue;

    public DropMetricValueEvent(Object source) {
        super(source);
        this.metricValue = (MetricValue) source;
    }

    public static MetricValueProto toMetricValueProto(DropMetricValueEvent dropMetricEvent) {
        MetricValue metricValue = dropMetricEvent.metricValue;
        MetricValueProto metricValueProto = MetricValueProto.newBuilder()
                .setMetric(metricValue.getMetric())
                .putAllTags(metricValue.getTags())
                .setEventTime(metricValue.getEventTime().atZone(ZoneId.systemDefault()).toEpochSecond())
                .setValue(metricValue.getValue())
                .setTtl(metricValue.getTtl())
                .build();
        return metricValueProto;
    }
}
