package data.platform.common.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * {
 *     "group_by": [
 *         {
 *             "name": "tag",
 *             "tags": [
 *                 "mo"
 *             ]
 *         }
 *     ]
 * }
 */
// 只支持tag类型的group
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QueryGroupBy {

    // 只支持tag方式
    @JsonProperty("name")
    private String name;

    @JsonProperty("tags")
    private List<String> tags;
}
