package data.platform.common.service.query;

import com.google.common.collect.Sets;
import data.platform.common.query.QueryAggregatorUnit;
import data.platform.common.query.QueryBuilder;
import data.platform.common.query.QueryMetric;
import data.platform.common.response.QueryResults;
import reactor.core.publisher.Mono;

import java.util.*;

public interface MetricResultQueryService {

    Mono<QueryResults> query(QueryBuilder queryBuilder);

    /**
     * Map<String, Set<String>> queryMetricTags = new HashMap<>();
     * queryMetricTags.put("MOC", ImmutableSet.of("NetworkDevice"));
     * queryMetricTags.put("MO", ImmutableSet.of("NetworkDevice.domain=\"defaultEngine\",uuid=\"00202a67e23142519d7365ea3a870b6f\"", "NetworkDevice.domain=\"defaultEngine\",uuid=\"01875700e9c6424e8effbacbeab20703\""));
     * queryMetricTags.put("Location", ImmutableSet.of("sh", "bj"));
     * <p>
     * results:
     * [
     * {
     * "MOC": "NetworkDevice",
     * "MO": "NetworkDevice.domain=\"defaultEngine\",uuid=\"01875700e9c6424e8effbacbeab20703\"",
     * "Location": "bj"
     * },
     * {
     * "MOC": "NetworkDevice",
     * "MO": "NetworkDevice.domain=\"defaultEngine\",uuid=\"01875700e9c6424e8effbacbeab20703\"",
     * "Location": "sh"
     * },
     * {
     * "MOC": "NetworkDevice",
     * "MO": "NetworkDevice.domain=\"defaultEngine\",uuid=\"00202a67e23142519d7365ea3a870b6f\"",
     * "Location": "bj"
     * },
     * {
     * "MOC": "NetworkDevice",
     * "MO": "NetworkDevice.domain=\"defaultEngine\",uuid=\"00202a67e23142519d7365ea3a870b6f\"",
     * "Location": "sh"
     * }
     * ]
     *
     * @param queryMetric
     * @return
     */
    default List<Map<String, String>> getMetricTags(QueryMetric queryMetric) {
        // 把多个tag拼成Map
        List<Set<Map<String, String>>> tagArray = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : queryMetric.getTags().entrySet()) {
            Set<Map<String, String>> tagMaps = new HashSet<>();
            for (String tagValue : entry.getValue()) {
                Map<String, String> tags = new HashMap<>();
                tags.put(entry.getKey(), tagValue);
                tagMaps.add(tags);
            }
            tagArray.add(tagMaps);
        }
        // 笛卡尔集合
        Set<List<Map<String, String>>> cartesians = Sets.cartesianProduct(tagArray);

        List<Map<String, String>> queryTags = new ArrayList<>();
        for (List<Map<String, String>> cartesian : cartesians) {
            Map<String, String> queryTag = new HashMap<>();
            for (Map<String, String> child : cartesian) {
                queryTag.putAll(child);
            }
            queryTags.add(queryTag);
        }
        return queryTags;
    }

    default QueryAggregatorUnit getQueryAggregatorUnit(QueryMetric queryMetric) {
        QueryAggregatorUnit aggregatorUnit = QueryAggregatorUnit.PLAIN;
        if (Objects.nonNull(queryMetric.getAggregators()) && queryMetric.getAggregators().size() > 0) {
            aggregatorUnit = queryMetric.getAggregators().get(0).getAggregatorUnit();
        }
        return aggregatorUnit;
    }
}
