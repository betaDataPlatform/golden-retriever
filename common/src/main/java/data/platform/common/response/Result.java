package data.platform.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class Result {

    @JsonProperty("name")
    private String name;

    @JsonProperty("tags")
    private Map<String, Set<String>> tags;

    @JsonProperty("values")
    private List<DataPoint> dataPoints;

    @JsonProperty("group_by")
    private List<TagGroupResult> groupResults = new ArrayList<>();

    @JsonIgnore
    private String tagJson;

    public Result(String name, String tagJson) {
        this.name = name;
        this.tagJson = tagJson;
    }

    public Result(String name, Map<String, Set<String>> tags, List<DataPoint> dataPoints) {
        this.name = name;
        this.tags = tags;
        this.dataPoints = dataPoints;
    }
}
