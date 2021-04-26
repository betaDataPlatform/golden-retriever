package data.platform.cassandra.internal.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import data.platform.common.domain.MetricTag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@ConditionalOnBean(name = "cassandraConfig")
@Configuration
public class CassandraCacheConfig {

    @Value("${cassandra.caffeine.expire.metric}")
    private int metricExpire;

    @Value("${cassandra.caffeine.expire.metricTag}")
    private int metricTagExpire;

    @Value("${cassandra.caffeine.capacity.metric}")
    private int metricCapacity;

    @Value("${cassandra.caffeine.capacity.metricTag}")
    private int metricTagCapacity;

    /**
     * key: metric name
     * value: metric db id
     *
     * @return
     */
    @Bean
    public Cache<String, Optional> tsMetricCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(metricExpire, TimeUnit.MINUTES)
                // 初始的缓存空间大小
                .initialCapacity(metricCapacity)
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
