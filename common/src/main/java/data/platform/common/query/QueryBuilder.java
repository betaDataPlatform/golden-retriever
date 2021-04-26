package data.platform.common.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 *  "metrics": [
 *      {
 *          "tags": {
 *              "mo": [
 *                  "NetworkDevice.domain=\"defaultEngine\",uuid=\"00202a67e23142519d7365ea3a870b6f\"",
 *                  "NetworkDevice.domain=\"defaultEngine\",uuid=\"01875700e9c6424e8effbacbeab20703\""
 *              ],
 *              "moc": [
 *                  "NetworkDevice"
 *              ]
 *          },
 *          "name": "CPU",
 *          "group_by": [
 *              {
 *                  "name": "tag",
 *                  "tags": [
 *                      "mo"
 *                  ]
 *              }
 *          ],
 *          "aggregators": [
 *              {
 *                  "name": "sum",
 *                  "sampling": {
 *                      "value": "1",
 *                      "unit": "hours"
 *                  },
 *                  "align_start_time": true
 *              }
 *          ]
 *      },
 *      {
 *          "tags": {
 *              "mo": [
 *                  "NetworkDevice.domain=\"defaultEngine\",uuid=\"00a997e2fdc2425aa797c06a3304a96e\""
 *              ]
 *          },
 *          "name": "MEM"
 *      }
 *  ],
 *  "plugins": [],
 *  "cache_time": 0,
 *  "start_absolute": 1615305600000,
 *  "end_absolute": 1615312800000
 * }
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class QueryBuilder {

    @JsonProperty("start_absolute")
    private long beginDate;

    @JsonProperty("end_absolute")
    private long endDate;

    @JsonProperty("metrics")
    private List<QueryMetric> metrics = new ArrayList<>();

}