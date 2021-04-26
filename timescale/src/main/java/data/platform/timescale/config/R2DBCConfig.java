package data.platform.timescale.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.r2dbc.core.DatabaseClient;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@ConditionalOnBean(name = "timeScaleConfig")
@Configuration
@RequiredArgsConstructor
public class R2DBCConfig {

    final TimeScaleConfig databaseConfig;

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(DRIVER, "postgresql")
                        .option(HOST, databaseConfig.getHost())
                        .option(PORT, databaseConfig.getPort())
                        .option(USER, databaseConfig.getUsername())
                        .option(PASSWORD, databaseConfig.getPassword())
                        .option(DATABASE, databaseConfig.getDatabase())
                        .option(MAX_SIZE, databaseConfig.getMaxSize())
                        .build());
    }

    @Bean
    public R2dbcEntityOperations r2dbcEntityOperations(ConnectionFactory connectionFactory) {
        DatabaseClient databaseClient = DatabaseClient.create(connectionFactory);
        return new R2dbcEntityTemplate(databaseClient, PostgresDialect.INSTANCE);
    }

    @Bean
    public DatabaseClient databaseClient(R2dbcEntityOperations r2dbcEntityOperations) {
        return r2dbcEntityOperations.getDatabaseClient();
    }
}
