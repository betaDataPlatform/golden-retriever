package data.platform.common.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {
 *  "tags": {
 *      "moc": [
 *          "LinuxServer"
 *      ],
 *      "mo": [
 *          "LinuxServer.domain=\"defaultEngine\",uuid=\"0090003f\"",
 *          "LinuxServer.domain=\"defaultEngine\",uuid=\"0070001e\""
 *      ]
 *  }
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QueryMetric {

    // 指标名称
    @JsonProperty("name")
    private String metric;

    // 要查询的mo
    @JsonProperty("tags")
    private Map<String, Set<String>> tags;

    // 分组，业务太复杂，不实现功能，默认按照tagJson分组
    @JsonProperty("group_by")
    private List<QueryGroupBy> groupers;

    // 函数名称
    // 理论上可以支持多个函数，但在返回结果中，不能区分是哪个函数的值，所以在实际使用时，只有一个函数才有业务上的意义
    // 业务实现上只取第一个函数，其它函数不处理
    @JsonProperty("aggregators")
    private List<QueryAggregator> aggregators;
}