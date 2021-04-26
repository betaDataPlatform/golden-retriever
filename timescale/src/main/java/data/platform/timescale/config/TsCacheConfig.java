package data.platform.timescale.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import data.platform.common.domain.MetricTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@ConditionalOnBean(name = "timeScaleConfig")
@Configuration
@Slf4j
public class TsCacheConfig {

    @Value("${ts.caffeine.expire.metric}")
    private int metricExpire;

    @Value("${ts.caffeine.expire.tag}")
    private int tagExpire;

    @Value("${ts.caffeine.expire.metricTag}")
    private int metricTagExpire;

    @Value("${ts.caffeine.capacity.metric}")
    private int metricCapacity;

    @Value("${ts.caffeine.capacity.tag}")
    private int tagCapacity;

    @Value("${ts.caffeine.capacity.metricTag}")
    private int metricTagCapacity;

    /**
     * key: metric name
     * value: metric db id
     *
     * @return
     */
    @Bean
    public Cache<String, Integer> tsMetricCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(metricExpire, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(metricCapacity)
                .build();
    }

    /**
     * key: tag name
     * value: tag db id
     *
     * @return
     */
    @Bean
    public Cache<String, Integer> tsTagCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(tagExpire, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(tagCapacity)
                .build();
    }

    @Bean
    public Cache<String, Set<MetricTag>> tsMetricTagCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(metricTagExpire, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(metricTagCapacity)
                .build();
    }

}
