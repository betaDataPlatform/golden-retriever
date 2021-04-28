package data.platform.common.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * align_start_time: true
 * name: "sum"
 * sampling: {value: "1", unit: "hours"}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class QueryAggregator {

    @JsonProperty("name")
    private String name;

    @JsonProperty("sampling")
    // 业务上只实现开始时间和结束时间的统计
    // kairosdb支持通过时间unit进行分片统计，这个业务不实现
    private QuerySampling sampling;

    // 默认使用这个值
    // 返回的时间使用查询的开始时间
    @JsonProperty("align_start_time")
    private Boolean alignStartTime;

    @JsonProperty("align_end_time")
    private Boolean alignEndTime;

    @JsonProperty("align_sampling")
    private Boolean alignSampling;

    @JsonProperty("start_time")
    private Long startTime;

    @JsonIgnoreProperties
    private QueryAggregatorUnit aggregatorUnit;

    public QueryAggregatorUnit getAggregatorUnit() {
         return QueryAggregatorUnit.getAggregatorUnitFromDesc(name);
    }
}
