package data.platform.common.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * {
 *     "queries": [
 *         {
 *             "sample_size": 48,
 *             "results": [
 *                 {
 *                     "name": "CPU",
 *                     "group_by": [
 *                         {
 *                             "name": "tag",
 *                             "tags": [
 *                                 "mo"
 *                             ],
 *                             "group": {
 *                                 "mo": "NetworkDevice.domain=\"defaultEngine\",uuid=\"00202a67e23142519d7365ea3a870b6f\""
 *                             }
 *                         },
 *                         {
 *                             "name": "type",
 *                             "type": "number"
 *                         }
 *                     ],
 *                     "tags": {
 *                         "mo": [
 *                             "NetworkDevice.domain=\"defaultEngine\",uuid=\"00202a67e23142519d7365ea3a870b6f\""
 *                         ],
 *                         "moc": [
 *                             "NetworkDevice"
 *                         ],
 *                         "sourceId": [
 *                             "BTPM_T00_S01[30.0.16.116:30100]"
 *                         ]
 *                     },
 *                     "values": [
 *                         [
 *                             1615305600000,
 *                             120
 *                         ],
 *                         [
 *                             1615309200000,
 *                             120
 *                         ]
 *                     ]
 *                 },
 *                 {
 *                     "name": "CPU",
 *                     "group_by": [
 *                         {
 *                             "name": "tag",
 *                             "tags": [
 *                                 "mo"
 *                             ],
 *                             "group": {
 *                                 "mo": "NetworkDevice.domain=\"defaultEngine\",uuid=\"01875700e9c6424e8effbacbeab20703\""
 *                             }
 *                         },
 *                         {
 *                             "name": "type",
 *                             "type": "number"
 *                         }
 *                     ],
 *                     "tags": {
 *                         "mo": [
 *                             "NetworkDevice.domain=\"defaultEngine\",uuid=\"01875700e9c6424e8effbacbeab20703\""
 *                         ],
 *                         "moc": [
 *                             "NetworkDevice"
 *                         ],
 *                         "sourceId": [
 *                             "BTPM_T00_S01[30.0.16.116:30100]"
 *                         ]
 *                     },
 *                     "values": [
 *                         [
 *                             1615305600000,
 *                             120
 *                         ],
 *                         [
 *                             1615309200000,
 *                             120
 *                         ]
 *                     ]
 *                 }
 *             ]
 *         },
 *         {
 *             "sample_size": 24,
 *             "results": [
 *                 {
 *                     "name": "MEM",
 *                     "group_by": [
 *                         {
 *                             "name": "type",
 *                             "type": "number"
 *                         }
 *                     ],
 *                     "tags": {
 *                         "mo": [
 *                             "NetworkDevice.domain=\"defaultEngine\",uuid=\"00a997e2fdc2425aa797c06a3304a96e\""
 *                         ],
 *                         "moc": [
 *                             "NetworkDevice"
 *                         ],
 *                         "sourceId": [
 *                             "BTPM_T00_S01[30.0.16.116:30100]"
 *                         ]
 *                     },
 *                     "values": [
 *                         [
 *                             1615305793285,
 *                             50
 *                         ],
 *                         [
 *                             1615306093285,
 *                             50
 *                         ],
 *                         [
 *                             1615306393286,
 *                             50
 *                         ],
 *                         [
 *                             1615306693286,
 *                             50
 *                         ],
 *                         [
 *                             1615306993285,
 *                             50
 *                         ],
 *                         [
 *                             1615307293285,
 *                             50
 *                         ],
 *                         [
 *                             1615307593285,
 *                             50
 *                         ],
 *                         [
 *                             1615307893286,
 *                             50
 *                         ],
 *                         [
 *                             1615308193285,
 *                             50
 *                         ],
 *                         [
 *                             1615308493285,
 *                             50
 *                         ],
 *                         [
 *                             1615308793285,
 *                             50
 *                         ],
 *                         [
 *                             1615309093285,
 *                             50
 *                         ],
 *                         [
 *                             1615309393286,
 *                             50
 *                         ],
 *                         [
 *                             1615309693285,
 *                             50
 *                         ],
 *                         [
 *                             1615309993286,
 *                             50
 *                         ],
 *                         [
 *                             1615310293286,
 *                             50
 *                         ],
 *                         [
 *                             1615310593285,
 *                             50
 *                         ],
 *                         [
 *                             1615310893286,
 *                             50
 *                         ],
 *                         [
 *                             1615311193285,
 *                             50
 *                         ],
 *                         [
 *                             1615311493286,
 *                             50
 *                         ],
 *                         [
 *                             1615311793285,
 *                             50
 *                         ],
 *                         [
 *                             1615312093286,
 *                             50
 *                         ],
 *                         [
 *                             1615312393285,
 *                             50
 *                         ],
 *                         [
 *                             1615312693285,
 *                             50
 *                         ]
 *                     ]
 *                 }
 *             ]
 *         }
 *     ]
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResults {

    @JsonProperty("queries")
    private List<QueryResult> queryResults = new ArrayList<>();
}
