package data.platform.monitor.config;

import data.platform.monitor.domain.DataSetMeterRegistry;
import data.platform.monitor.domain.DataSetRegistryConfig;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class DataSetMicroMeterConfig {

    final ApplicationEventPublisher applicationEventPublisher;

    @Bean
    public DataSetMeterRegistry dataSetMeterRegistry() {
        DataSetRegistryConfig dataSetRegistryConfig = new DataSetRegistryConfig() {

            String enabled = "true";

            @Override
            public String get(String key) {
                if (key.equals(prefix() + ".enabled")) {
                    return enabled;
                } else {
                    return null;
                }
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(30);
            }
        };

        DataSetMeterRegistry dataSetMeterRegistry = new DataSetMeterRegistry(dataSetRegistryConfig, Clock.SYSTEM, applicationEventPublisher);
        dataSetMeterRegistry.config().commonTags("application", "betasoft.dc.dataset");

        new JvmMemoryMetrics().bindTo(dataSetMeterRegistry);
        new ProcessorMetrics().bindTo(dataSetMeterRegistry);
        //new JvmThreadMetrics().bindTo(btdpMeterRegistry);
        //new CassandraMetrics("127.0.0.1", 7199).bindTo(btdpMeterRegistry);

        return dataSetMeterRegistry;
    }
}
