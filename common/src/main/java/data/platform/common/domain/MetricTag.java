package data.platform.common.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * CPU location china {"host": "30.0.244.25","location": "china"}
 * CPU location china {"host": "192.168.1.1","location": "china"}
 * CPU host 30.0.244.25 {"host": "30.0.244.25","location": "china"}
 * CPU host 192.168.1.1 {"host": "30.0.244.25","location": "china"}
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class MetricTag {

    // 指标
    private String metric;

    // 标签名
    private String tagName;

    // 标签值
    private String tagValue;

    // 标签
    private Map<String, String> tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricTag metricTag = (MetricTag) o;

        if (getMetric() != null ? !getMetric().equals(metricTag.getMetric()) : metricTag.getMetric() != null)
            return false;
        if (getTagName() != null ? !getTagName().equals(metricTag.getTagName()) : metricTag.getTagName() != null)
            return false;
        if (getTagValue() != null ? !getTagValue().equals(metricTag.getTagValue()) : metricTag.getTagValue() != null)
            return false;
        return getTag() != null ? getTag().equals(metricTag.getTag()) : metricTag.getTag() == null;
    }

    @Override
    public int hashCode() {
        int result = getMetric() != null ? getMetric().hashCode() : 0;
        result = 31 * result + (getTagName() != null ? getTagName().hashCode() : 0);
        result = 31 * result + (getTagValue() != null ? getTagValue().hashCode() : 0);
        result = 31 * result + (getTag() != null ? getTag().hashCode() : 0);
        return result;
    }

    public String cacheKey() {
        return String.join("|", this.metric, this.tagName, this.tagValue);
    }

    public static String cacheKey(String metric, String tagName, String tagValue) {
        return String.join("|", metric, tagName, tagValue);
    }

    public String getTagJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(tag);
        } catch (Exception ex) {
            return "";
        }
    }
}
