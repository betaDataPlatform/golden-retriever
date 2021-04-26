package data.platform.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class MetricValue {

    private String metric;

    /**
     * 一个SortedMap,转换成string
     */
    private Map<String, String> tags;

    private LocalDateTime eventTime;

    private Double value;

    private Integer ttl;
}
