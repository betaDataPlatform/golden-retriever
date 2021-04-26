package data.platform.monitor.domain;

import io.micrometer.core.instrument.step.StepRegistryConfig;

public interface DataSetRegistryConfig extends StepRegistryConfig {

    DataSetRegistryConfig DEFAULT = k -> null;

    @Override
    default String prefix() {
        return "goldenRetriever";
    }

}

