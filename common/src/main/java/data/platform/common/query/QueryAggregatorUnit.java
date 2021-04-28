package data.platform.common.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * <option value="avg">AVG</option>
 * <option value="count">COUNT</option>
 * <option value="dev">DEV</option>
 * <option value="diff">DIFF</option>
 * <option value="div">DIV</option>
 * <option value="filter">FILTER</option>
 * <option value="first">FIRST</option>
 * <option value="gaps">GAPS</option>
 * <option value="last">LAST</option>
 * <option value="least_squares">LEAST SQUARES</option>
 * <option value="max">MAX</option>
 * <option value="min">MIN</option>
 * <option value="percentile">PERCENTILE</option>
 * <option value="rate">RATE</option>
 * <option value="sampler">SAMPLER</option>
 * <option value="save_as">SAVE AS</option>
 * <option value="scale">SCALE</option>
 * <option value="sum">SUM</option>
 * <option value="trim">TRIM</option>
 */
@JsonDeserialize(using = CustomAggregatorUnitDeserializer.class)
public enum QueryAggregatorUnit {

    PLAIN("plain"), AVG("avg"), COUNT("count"), MAX("max"), MIN("min"), SUM("sum");

    private String description;

    QueryAggregatorUnit(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static QueryAggregatorUnit getAggregatorUnitFromDesc(String description) {
        switch (description) {
            case "avg":
                return AVG;
            case "count":
                return COUNT;
            case "max":
                return MAX;
            case "min":
                return MIN;
            case "sum":
                return SUM;
            default:
                return PLAIN;
        }
    }
}
