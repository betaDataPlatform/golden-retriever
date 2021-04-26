package data.platform.timescale.internal.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.collect.Sets;
import data.platform.common.domain.MetricTag;
import data.platform.timescale.persistence.mapping.MetricTagEO;
import data.platform.timescale.persistence.repository.TsMetricRepository;
import data.platform.timescale.persistence.repository.TsMetricTagRepository;
import data.platform.timescale.persistence.repository.TsTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 时序数据由指标+tag标识识一个数据点
 * tag由Map<String,String>组成
 * 通过指标+tagKey+tagValue可以定位到多个tag
 */
@ConditionalOnBean(name = "timeScaleConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class TsCacheService {

    final Cache<String, Integer> tsMetricCache;

    final Cache<String, Integer> tsTagCache;

    final Cache<String, Set<MetricTag>> tsMetricTagCache;

    final TsMetricRepository metricRepository;

    final TsTagRepository tagRepository;

    final TsMetricTagRepository metricTagRepository;

    @PostConstruct
    public void init() {
        log.info("timescale metric tag cache load starting.........");
        // 缓存加载完成后，再启动处理流程
        metricTagRepository.findAll()
                .doOnNext(metricTagEO -> {
                    tsMetricCache.put(metricTagEO.getMetric(), metricTagEO.getMetricId());
                    tsTagCache.put(metricTagEO.getTag(), metricTagEO.getTagId());

                    MetricTag metricTag = metricTagEO.toMetricTag();
                    String key = metricTag.cacheKey();
                    Set<MetricTag> metricTags = tsMetricTagCache.get(key, k -> new HashSet<>());
                    metricTags.add(metricTag);
                })
                .collectList()
                .block();
        log.info("timescale metric tag cache load finish.......");
        log.info("metric cache size is: [{}].", tsMetricCache.asMap().size());
        log.info("tag cache size is: [{}].", tsTagCache.asMap().size());
        log.info("metric tag cache size is: [{}].", tsMetricTagCache.asMap().size());
    }

    public Mono<Integer> metricPutCache(String metric) {
        Integer id = tsMetricCache.getIfPresent(metric);
        if (Objects.isNull(id)) {
            return metricRepository.save(metric)
                    .onErrorResume(e -> metricRepository.findByName(metric)
                            .map(eo -> eo.getId()))
                    .doOnNext(metricId -> tsMetricCache.put(metric, metricId));
        } else {
            return Mono.just(id);
        }
    }

    public Mono<Integer> tagPutCache(String tag) {
        Integer id = tsTagCache.getIfPresent(tag);
        if (Objects.isNull(id)) {
            return tagRepository.save(tag)
                    .onErrorResume(e -> tagRepository.findByTag(tag)
                            .map(eo -> eo.getId()))
                    .doOnNext(tagId -> tsTagCache.put(tag, tagId));
        } else {
            return Mono.just(id);
        }
    }

    public Mono<MetricTag> metricTagPutCache(MetricTag metricTag) {
        String key = metricTag.cacheKey();
        Set<MetricTag> metricTags = tsMetricTagCache.get(key, k -> new HashSet<>());
        Optional<MetricTag> metricTagOptional = metricTags.stream()
                .filter(mt -> mt.equals(metricTag))
                .findAny();
        if (!metricTagOptional.isPresent()) {
            return createMetricTag(metricTag);
        } else {
            return Mono.just(metricTagOptional.get());
        }
    }

    public Optional<Integer> getMetricId(String metric) {
        Integer id = tsMetricCache.getIfPresent(metric);
        if (Objects.isNull(id)) {
            return Optional.empty();
        } else {
            return Optional.of(id);
        }
    }

    public Optional<Integer> getTagId(String tag) {
        Integer id = tsTagCache.getIfPresent(tag);
        if (Objects.isNull(id)) {
            return Optional.empty();
        } else {
            return Optional.of(id);
        }
    }

    public Boolean metricExist(String metric) {
        Integer id = tsMetricCache.getIfPresent(metric);
        if (Objects.isNull(id)) {
            return false;
        } else {
            return true;
        }
    }

    public Collection<String> matchingTag(String metric, Map<String, String> tags) {
        List<Set<String>> tagSets = new ArrayList<>();
        tags.forEach((tagName, tagValue) -> {
            Set<String> tempSet = tsMetricTagCache.get(
                    MetricTag.cacheKey(metric, tagName, tagValue),
                    k -> new HashSet<>())
                    .stream()
                    .map(MetricTag::getTagJson)
                    .collect(Collectors.toSet());
            tagSets.add(tempSet);
        });
        Set<String> intersection = tagSets.get(0);
        for (Set<String> scan : tagSets.subList(1, tagSets.size())) {
            intersection = Sets.intersection(intersection, scan);
        }
        return intersection;
    }

    // update.................
    private Mono<MetricTag> createMetricTag(MetricTag metricTag) {
        return Mono.defer(() -> {
                    Integer metricId = tsMetricCache.getIfPresent(metricTag.getMetric());
                    Integer tagId = tsTagCache.getIfPresent(metricTag.getTag());
                    MetricTagEO eo = new MetricTagEO();
                    eo.setMetric(metricTag.getMetric());
                    eo.setMetricId(metricId);
                    eo.setTagName(metricTag.getTagName());
                    eo.setTagValue(metricTag.getTagValue());
                    eo.setTag(metricTag.getTagJson());
                    eo.setTagId(tagId);
                    return metricTagRepository.save(eo);
                }
        )
                .onErrorResume(e -> metricTagRepository.findByMetricAndTag(metricTag.getMetric(), metricTag.getTagName(), metricTag.getTagValue(), tsTagCache.getIfPresent(metricTag.getTag())))
                .map(eo -> eo.toMetricTag())
                .doOnNext(e -> tsMetricTagCache.getIfPresent(metricTag.cacheKey()).add(e));
    }
}
