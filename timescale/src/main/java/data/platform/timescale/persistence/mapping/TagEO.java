package data.platform.timescale.persistence.mapping;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "tag")
public class TagEO {

    @Id
    private int id;

    @Column("tag_json")
    private String tagJson;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEO tagEO = (TagEO) o;

        return getId() == tagEO.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
