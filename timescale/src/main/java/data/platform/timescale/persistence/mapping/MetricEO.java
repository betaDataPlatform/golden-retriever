package data.platform.timescale.persistence.mapping;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "metric")
public class MetricEO {

    @Id
    private int id;

    @Column("name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricEO metricEO = (MetricEO) o;

        return getId() == metricEO.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
