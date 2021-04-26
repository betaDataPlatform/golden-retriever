package data.platform.timescale.persistence.mapping;

import data.platform.common.response.DataPoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "data_point")
public class DataPointEO {

    @Column("event_time")
    private Date eventTime;

    @Column("metric_id")
    private int metricId;

    @Column("tag_id")
    private int tagId;

    @Column("value")
    private Double value;

    @Column("metric")
    private String metric;

    @Column("tag_json")
    private Map<String, String> tagJson;

    public DataPoint toDataPoint() {
        DataPoint dataPoint = new DataPoint();
        dataPoint.setTimestamp(eventTime);
        dataPoint.setValue(value);
        return dataPoint;
    }
}
