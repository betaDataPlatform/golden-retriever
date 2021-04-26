package data.platform.cassandra.infra.persistence.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class MetricTagKey implements Serializable {

    @PrimaryKeyColumn(name = "metric", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String metric;

    @PrimaryKeyColumn(name = "tag_name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String tagName;

    @PrimaryKeyColumn(name = "tag_value", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private String tagValue;

    @PrimaryKeyColumn(name = "tag", ordinal = 4, type = PrimaryKeyType.CLUSTERED)
    private String tag;

}
