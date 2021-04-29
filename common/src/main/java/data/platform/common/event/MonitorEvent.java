package data.platform.common.event;

import data.platform.common.domain.MetricValue;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class MonitorEvent extends ApplicationEvent {

    private List<MetricValue> metricValues;

    public MonitorEvent(Object source) {
        super(source);
        this.metricValues = (List<MetricValue>) source;
    }
}
