package data.platform.timescale.persistence.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.platform.common.domain.MetricTag;
import lombok.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "metric_tag")
public class MetricTagEO {

    @Column("metric_id")
    private Integer metricId;

    @Column("tag_name")
    private String tagName;

    @Column("tag_value")
    private String tagValue;

    @Column("tag_id")
    private Integer tagId;

    @Column("metric")
    private String metric;

    @Column("tag")
    private String tag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricTagEO that = (MetricTagEO) o;

        if (getMetricId() != null ? !getMetricId().equals(that.getMetricId()) : that.getMetricId() != null)
            return false;
        if (getTagName() != null ? !getTagName().equals(that.getTagName()) : that.getTagName() != null) return false;
        if (getTagValue() != null ? !getTagValue().equals(that.getTagValue()) : that.getTagValue() != null)
            return false;
        return getTagId() != null ? getTagId().equals(that.getTagId()) : that.getTagId() == null;
    }

    @Override
    public int hashCode() {
        int result = getMetricId() != null ? getMetricId().hashCode() : 0;
        result = 31 * result + (getTagName() != null ? getTagName().hashCode() : 0);
        result = 31 * result + (getTagValue() != null ? getTagValue().hashCode() : 0);
        result = 31 * result + (getTagId() != null ? getTagId().hashCode() : 0);
        return result;
    }

    public Map<String, String> getTagMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(tag, Map.class);
        } catch (Exception ex) {
            return new HashMap<>();
        }
    }

    public MetricTag toMetricTag() {
        MetricTag metricTag = new MetricTag();
        metricTag.setMetric(metric);
        metricTag.setTagName(tagName);
        metricTag.setTagValue(tagValue);
        metricTag.setTag(getTagMap());
        return metricTag;
    }

}
