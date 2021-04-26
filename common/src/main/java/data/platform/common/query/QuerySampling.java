package data.platform.common.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QuerySampling {

    @JsonProperty("value")
    private int value;

    @JsonProperty("unit")
    private QuerySamplingUnit unit;

}
