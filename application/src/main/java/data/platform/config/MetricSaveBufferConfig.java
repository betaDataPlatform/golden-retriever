package data.platform.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "metric.save.buffer")
@Slf4j
@Setter
@Getter
public class MetricSaveBufferConfig {

    private int maxSize;

    private int maxTime;
}
