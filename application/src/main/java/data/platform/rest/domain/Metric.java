package data.platform.rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * [
 *  {
 *      "name": "archive_file_tracked",
 *      "datapoints": [[1359788400000, 123], [1359788300000, 13.2], [1359788410000, 23.1]],
 *      "tags": {
 *          "host": "server1",
 *          "data_center": "DC1"
 *      },
 *      "ttl": 300
 *  }
 * ]
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties
public class Metric {

    @JsonProperty("name")
    private String name;

    @JsonProperty("tags")
    private Map<String, String> tags;

    @JsonProperty("datapoints")
    private List<Object[]> samplePoints;

    // select metric,mo,ttl(value) from data_point;
    // second
    @JsonProperty("ttl")
    private int ttl;

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}