package data.platform.common.query;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CustomUnitDeserializer.class)
public enum QuerySamplingUnit {

    MILLISECONDS("milliseconds"), SECONDS("seconds"), MINUTES("minutes"),
    HOURS("hours"), DAYS("days"), WEEKS("weeks"),
    MONTHS("months"), YEARS("years");

    private String description;

    QuerySamplingUnit(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
