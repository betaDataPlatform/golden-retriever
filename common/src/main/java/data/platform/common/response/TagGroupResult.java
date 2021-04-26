package data.platform.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TagGroupResult {

    @JsonProperty("name")
    private String name = "tag";

    @JsonProperty("tags")
    private List<String> tags = new ArrayList<>();

    @JsonProperty("group")
    private Map<String, String> group = new HashMap<>();
}
