package data.platform.timescale.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Profile("timescale")
@Configuration
@ConfigurationProperties(prefix = "timescale")
@Slf4j
@Setter
@Getter
public class TimeScaleConfig {

    private String host;

    private int port;

    private String database;

    private String username;

    private String password;

    private int initialSize;

    private int maxSize;

    @PostConstruct
    public void init() {
        log.info("start timescale module..................");
    }
}
