package data.platform.cassandra.infra.persistence.mapping;

import data.platform.common.domain.MetricTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(value = "metric_tag")
public class MetricTagEO {

    @PrimaryKey
    private MetricTagKey key;

    public MetricTag toMetricTag() {
        MetricTag metricTag = new MetricTag();
        metricTag.setMetric(this.getKey().getMetric());
        metricTag.setTagName(this.getKey().getTagName());
        metricTag.setTagValue(this.getKey().getTagValue());
        metricTag.setTag(this.getKey().getTag());
        return metricTag;
    }

    public static MetricTagEO toMetricTagEO(MetricTag metricTag) {
        MetricTagKey key = new MetricTagKey();
        key.setMetric(metricTag.getMetric());
        key.setTagName(metricTag.getTagName());
        key.setTagValue(metricTag.getTagValue());
        key.setTag(metricTag.getTag());

        MetricTagEO eo = new MetricTagEO();
        eo.setKey(key);

        return eo;
    }
}
