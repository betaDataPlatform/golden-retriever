package data.platform.cassandra.internal.cache;

import com.datastax.oss.driver.shaded.guava.common.collect.Sets;
import com.github.benmanes.caffeine.cache.Cache;
import data.platform.cassandra.infra.persistence.mapping.MetricTagEO;
import data.platform.cassandra.infra.persistence.repository.CassandraMetricTagRepository;
import data.platform.common.domain.MetricTag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 一个指标+一个tag唯一标识一个数据点
 * tag由Map<String,String>组成
 * 指标+tagKey+tagValue必定属于一个tag
 */
@ConditionalOnBean(name = "cassandraConfig")
@Slf4j
@Service
@RequiredArgsConstructor
public class CassandraCacheService {

    final Cache<String, Optional> cassandraMetricCache;

    final Cache<String, Set<MetricTag>> cassandraMetricTagCache;

    final CassandraMetricTagRepository cassandraMetricTagRepository;

    @PostConstruct
    public void init() {
        log.info("cassandra metric tag cache load starting.........");
        cassandraMetricTagRepository.findAll()
                .map(MetricTagEO::toMetricTag)
                .doOnNext(metricTag -> add(metricTag))
                .collectList()
                .subscribe(metricTags -> {
                    log.info("cassandra metric tag cache load finish.......");
                    log.info("metric cache size is: [{}].", cassandraMetricCache.asMap().size());
                    log.info("metric tag cache size is: [{}].", cassandraMetricTagCache.asMap().size());
                });
    }

    public void add(MetricTag metricTag) {
        addMetric(metricTag.getMetric());

        Set<MetricTag> metricTags = cassandraMetricTagCache.get(metricTag.cacheKey(), k -> new HashSet<>());
        metricTags.add(metricTag);
    }

    private void addMetric(String metric) {
        Optional metricCache = cassandraMetricCache.getIfPresent(metric);
        if (Objects.isNull(metricCache)) {
            cassandraMetricCache.put(metric, Optional.empty());
        }
    }

    public Mono<Integer> metricTagPutCache(List<MetricTag> metricTags) {
        return Flux.fromIterable(metricTags.stream().collect(Collectors.toSet()))
                .map(metricTag -> {
                    String key = metricTag.cacheKey();
                    Set<MetricTag> mts = cassandraMetricTagCache.get(key, k -> new HashSet<>());
                    Optional<MetricTag> metricTagOptional = mts.stream()
                            .filter(mt -> mt.equals(metricTag))
                            .findAny();
                    if (!metricTagOptional.isPresent()) {
                        metricTagOptional = Optional.of(metricTag);
                    } else {
                        metricTagOptional = Optional.empty();
                    }
                    return metricTagOptional;
                })
                .filter(metricTagOptional -> metricTagOptional.isPresent())
                .map(metricTagOptional -> metricTagOptional.get())
                .collectList()
                .flatMap(mts -> createMetricTag(mts));
    }

    public Collection<String> matchingTagByMetric(String metric) {
        List<MetricTag> metricTags = getAllMetricTags();
        return metricTags.stream()
                .filter(metricTag -> metricTag.getMetric().equals(metric))
                .map(metricTag -> metricTag.getTag())
                .collect(Collectors.toSet());
    }

    public Collection<String> matchingTag(String metric, Map<String, String> tags) {
        List<Set<String>> tagSets = new ArrayList<>();
        tags.forEach((tagName, tagValue) -> {
            Set<String> tempSet = cassandraMetricTagCache.get(
                    MetricTag.cacheKey(metric, tagName, tagValue),
                    k -> new HashSet<>())
                    .stream()
                    .map(MetricTag::getTag)
                    .collect(Collectors.toSet());
            tagSets.add(tempSet);
        });
        Set<String> intersection = tagSets.get(0);
        for (Set<String> scan : tagSets.subList(1, tagSets.size())) {
            intersection = Sets.intersection(intersection, scan);
        }
        return intersection;
    }

    public Set<String> getAllMetrics() {
        return cassandraMetricCache.asMap().keySet();
    }

    public List<MetricTag> getAllMetricTags() {
        return cassandraMetricTagCache.asMap().values().stream().
                flatMap(metricTags -> metricTags.stream())
                .collect(Collectors.toList());
    }

    private Mono<Integer> createMetricTag(List<MetricTag> metricTags) {
        return Flux.fromIterable(metricTags)
                .map(metricTag -> MetricTagEO.toMetricTagEO(metricTag))
                .flatMap(metricTagEO -> cassandraMetricTagRepository.save(metricTagEO))
                .map(metricTagEO -> metricTagEO.toMetricTag())
                .doOnNext(metricTag -> {
                    add(metricTag);
                })
                .collectList()
                .map(List::size);

    }

}
