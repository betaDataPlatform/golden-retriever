package data.platform.event.process;

import data.platform.common.event.DropMetricValueEvent;
import data.platform.common.event.MetricValueEvent;
import data.platform.common.service.command.DropMetricValuePersistence;
import data.platform.common.service.command.MetricValueCommandService;
import data.platform.config.MetricSaveBufferConfig;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class MetricValueEventProcess {

    final ApplicationContext applicationContext;

    final DropMetricValuePersistence dropMetricValuePersistence;

    final MetricValueCommandService metricValueCommandService;

    final MeterRegistry dataSetMeterRegistry;

    final MetricSaveBufferConfig metricSaveBufferConfig;

    Counter saveCounter;

    Counter dropCounter;

    @PostConstruct
    public void init() {

        saveCounter = Counter.builder("dp.metricValue.count.save")
                .register(dataSetMeterRegistry);

        dropCounter = Counter.builder("dp.metricValue.count.drop")
                .register(dataSetMeterRegistry);

        Flux<MetricValueEvent> metricValueEventFlux = Flux.create(fluxSink -> {
            ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
            context.addApplicationListener((ApplicationListener<MetricValueEvent>) metricValueEvent -> {
                fluxSink.next(metricValueEvent);
            });
        });
        bizProcessPipe(metricValueEventFlux)
                .subscribe(count -> saveCounter.increment(count));
    }

    private Flux<Integer> bizProcessPipe(Flux<MetricValueEvent> metricValueEventFlux) {
        // -Dreactor.bufferSize.small=20 ??????????????????????????????????????????reactor???????????????????????????????????????overflow?????????????????????256
        // ????????????onBackpressureBuffer?????????????????????????????????reactor.bufferSize.small??????
        // ???????????????onBackpressureBuffer+reactor.bufferSize.small
        return metricValueEventFlux
                // maxSize???????????????????????????reactor????????????????????????????????????????????????
                .onBackpressureBuffer(8,
                        metricEvent -> {
                            // ??????????????????????????????????????????????????????
                            dropCounter.increment();
                            dropMetricValuePersistence.addDropEvent(new DropMetricValueEvent(metricEvent.getMetricValue()));
                        },
                        BufferOverflowStrategy.DROP_OLDEST)
                .publishOn(Schedulers.newParallel("metrics", 2))
                //.doOnNext(metricValueEvent -> log.info(metricValueEvent.getMetricValue().toString()))
                .bufferTimeout(metricSaveBufferConfig.getMaxSize(), Duration.ofMillis(metricSaveBufferConfig.getMaxTime()))
                .map(metricValueEvents -> metricValueEvents.stream().map(MetricValueEvent::getMetricValue).collect(Collectors.toList()))
                .flatMap(metricValues -> metricValueCommandService.saveAll(metricValues));
    }
}
