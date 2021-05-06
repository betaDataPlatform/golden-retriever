package data.platform.common.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@Builder
public class MetricValue {

    private String metric;

    /**
     * 一个SortedMap,转换成string
     */
    private Map<String, String> tag;

    private LocalDateTime eventTime;

    private Double value;

    public String getTagJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> sortedMap = new TreeMap<>();
            sortedMap.putAll(tag);
            return objectMapper.writeValueAsString(sortedMap);
        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public String toString() {
        return "MetricValue{" +
                "metric='" + metric + '\'' +
                ", tag=" + tag +
                ", eventTime=" + eventTime +
                '}';
    }
}
