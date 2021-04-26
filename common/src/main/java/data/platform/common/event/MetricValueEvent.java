package data.platform.common.event;

import data.platform.common.domain.MetricValue;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MetricValueEvent extends ApplicationEvent {

    private MetricValue metricValue;

    public MetricValueEvent(Object source) {
        super(source);
        this.metricValue = (MetricValue) source;
    }
}
