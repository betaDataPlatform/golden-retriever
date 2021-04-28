package data.platform.common.response;

import lombok.Data;

import java.util.Date;

@Data
public class DataPoint {

    private long timestamp;

    private Object value;
}
