package data.platform.timescale;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import data.platform.common.query.*;
import data.platform.common.response.QueryResults;
import data.platform.common.service.query.MetricResultQueryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
@ActiveProfiles("timescale")
@Slf4j
public class TsMetricResultQueryServiceTest {

    @Autowired
    MetricResultQueryService metricResultQueryService;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void testQuery() throws JsonProcessingException {
        QueryBuilder queryBuilder = new QueryBuilder();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalDateTime = LocalDateTime.parse("2021-04-30 10:00:00", df);
        LocalDateTime endLocalDateTime = LocalDateTime.parse("2021-04-30 10:59:59", df);
        Date beginDateTime = Date.from(beginLocalDateTime.atZone( ZoneId.systemDefault()).toInstant());
        Date endDateTime = Date.from(endLocalDateTime.atZone( ZoneId.systemDefault()).toInstant());
        queryBuilder.setBeginDate(beginDateTime.getTime());
        queryBuilder.setEndDate(endDateTime.getTime());

        QueryMetric queryMetric = new QueryMetric();
        queryMetric.setMetric("CPU_LOAD");

        QueryGroupBy queryGroupBy = new QueryGroupBy();
        queryGroupBy.setName("tag");
        queryGroupBy.setTags(Arrays.asList("host"));
        queryMetric.setGroupers(Arrays.asList(queryGroupBy));

        Map<String, Set<String>> queryMetricTags = new HashMap<>();
        queryMetricTags.put("host", ImmutableSet.of("30.0.0.1","30.0.0.3"));
        queryMetricTags.put("location", ImmutableSet.of("sh"));
        queryMetric.setTags(queryMetricTags);

        queryBuilder.setMetrics(Arrays.asList(queryMetric));
        Mono<QueryResults> queryResultsMono = metricResultQueryService.query(queryBuilder);
        QueryResults queryResults= queryResultsMono.block();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat jsonDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(jsonDf);
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryResults));
    }

    @Test
    public void testQueryTwoQueryMetric() throws JsonProcessingException {
        QueryBuilder queryBuilder = new QueryBuilder();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalDateTime = LocalDateTime.parse("2021-04-26 16:00:00", df);
        LocalDateTime endLocalDateTime = LocalDateTime.parse("2021-04-26 16:59:59", df);
        Date beginDateTime = Date.from(beginLocalDateTime.atZone( ZoneId.systemDefault()).toInstant());
        Date endDateTime = Date.from(endLocalDateTime.atZone( ZoneId.systemDefault()).toInstant());
        queryBuilder.setBeginDate(beginDateTime.getTime());
        queryBuilder.setEndDate(endDateTime.getTime());

        QueryMetric queryMetric1 = new QueryMetric();
        queryMetric1.setMetric("CPU_LOAD");

        QueryGroupBy queryGroupBy1 = new QueryGroupBy();
        queryGroupBy1.setName("tag");
        queryGroupBy1.setTags(Arrays.asList("host"));
        queryMetric1.setGroupers(Arrays.asList(queryGroupBy1));

        Map<String, Set<String>> queryMetricTags1 = new HashMap<>();
        queryMetricTags1.put("host", ImmutableSet.of("30.0.0.1"));
        queryMetricTags1.put("location", ImmutableSet.of("sh"));
        queryMetric1.setTags(queryMetricTags1);

        QueryMetric queryMetric2 = new QueryMetric();
        queryMetric2.setMetric("CPU_LOAD");

        QueryGroupBy queryGroupBy2 = new QueryGroupBy();
        queryGroupBy2.setName("tag");
        queryGroupBy2.setTags(Arrays.asList("host"));
        queryMetric2.setGroupers(Arrays.asList(queryGroupBy2));

        Map<String, Set<String>> queryMetricTags2 = new HashMap<>();
        queryMetricTags2.put("host", ImmutableSet.of("10.0.0.1"));
        queryMetricTags2.put("location", ImmutableSet.of("bj"));
        queryMetric2.setTags(queryMetricTags2);

        queryBuilder.setMetrics(Arrays.asList(queryMetric1, queryMetric2));


        Mono<QueryResults> queryResultsMono = metricResultQueryService.query(queryBuilder);
        QueryResults queryResults= queryResultsMono.block();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat jsonDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(jsonDf);
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryResults));
    }

    @Test
    public void testFunction() throws JsonProcessingException {
        QueryBuilder queryBuilder = new QueryBuilder();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginLocalDateTime = LocalDateTime.parse("2021-04-26 16:00:00", df);
        LocalDateTime endLocalDateTime = LocalDateTime.parse("2021-04-26 16:59:59", df);
        Date beginDateTime = Date.from(beginLocalDateTime.atZone( ZoneId.systemDefault()).toInstant());
        Date endDateTime = Date.from(endLocalDateTime.atZone( ZoneId.systemDefault()).toInstant());
        queryBuilder.setBeginDate(beginDateTime.getTime());
        queryBuilder.setEndDate(endDateTime.getTime());

        QueryMetric queryMetric1 = new QueryMetric();
        queryMetric1.setMetric("CPU_LOAD");

        QueryGroupBy queryGroupBy1 = new QueryGroupBy();
        queryGroupBy1.setName("tag");
        queryGroupBy1.setTags(Arrays.asList("host"));
        queryMetric1.setGroupers(Arrays.asList(queryGroupBy1));

        QueryAggregator aggregator1 = new QueryAggregator();
        aggregator1.setName(QueryAggregatorUnit.AVG.getDescription());
        queryMetric1.setAggregators(Arrays.asList(aggregator1));

        Map<String, Set<String>> queryMetricTags1 = new HashMap<>();
        queryMetricTags1.put("location", ImmutableSet.of("sh"));
        queryMetric1.setTags(queryMetricTags1);

        QueryMetric queryMetric2 = new QueryMetric();
        queryMetric2.setMetric("CPU_LOAD");

        QueryGroupBy queryGroupBy2 = new QueryGroupBy();
        queryGroupBy2.setName("tag");
        queryGroupBy2.setTags(Arrays.asList("host"));
        queryMetric2.setGroupers(Arrays.asList(queryGroupBy1));

        QueryAggregator aggregator2 = new QueryAggregator();
        aggregator2.setName(QueryAggregatorUnit.MAX.getDescription());
        queryMetric2.setAggregators(Arrays.asList(aggregator2));

        Map<String, Set<String>> queryMetricTags2 = new HashMap<>();
        queryMetricTags2.put("host", ImmutableSet.of("10.0.0.1","10.0.0.2"));
        queryMetricTags2.put("location", ImmutableSet.of("bj"));
        queryMetric2.setTags(queryMetricTags2);

        queryBuilder.setMetrics(Arrays.asList(queryMetric1, queryMetric2));

        Mono<QueryResults> queryResultsMono = metricResultQueryService.query(queryBuilder);
        QueryResults queryResults= queryResultsMono.block();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat jsonDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(jsonDf);
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(queryResults));
    }
}
