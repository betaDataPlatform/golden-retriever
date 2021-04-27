package data.platform.cassandra.infra.persistence.mapping;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyClass
public class DataPointKey implements Serializable {

    @PrimaryKeyColumn(name = "metric", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String metric;

    @PrimaryKeyColumn(name = "tag_json", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String tagJson;

    @PrimaryKeyColumn(name = "day", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private LocalDate partition;

    @PrimaryKeyColumn(name = "offset", ordinal = 3, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private LocalTime offset;
}
