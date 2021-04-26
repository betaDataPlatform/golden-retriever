package data.platform.cassandra.infra.persistence.mapping;

import data.platform.common.response.DataPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "data_point")
public class DataPointEO {

    @PrimaryKey
    private DataPointKey dataPointKey;

    @Column("value")
    private Double value;

    @Transient
    private Integer ttl;

    public DataPoint toDataPoint() {
        DataPoint dataPoint = new DataPoint();
        LocalDateTime localDateTime = LocalDateTime.of(dataPointKey.getPartition(),dataPointKey.getOffset());
        Date eventTime = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        dataPoint.setTimestamp(eventTime);
        dataPoint.setValue(value);
        return dataPoint;
    }
}