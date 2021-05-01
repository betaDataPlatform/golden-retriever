package data.platform.timescale.internal.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@ConditionalOnBean(name = "timeScaleConfig")
@Service
@Slf4j
@RequiredArgsConstructor
public class DataRetentionPolicyService {

    final DatabaseClient databaseClient;

    @Value("${retention.policy.days}")
    private int retentionDays;

    private static final String DELETE_SQL = "SELECT remove_retention_policy('data_point',true);";

    private static final String CREATE_SQL = "SELECT add_retention_policy('data_point', INTERVAL '%d days', true);";

    @PostConstruct
    public void init() {
        // 结果在timescaledb_config bgw_job中查看
        String createSql = String.format(CREATE_SQL, retentionDays);
        log.info("retentionDays sql: [{}]", createSql);

        Mono.from(databaseClient.sql(DELETE_SQL)
                .then())
                .then(databaseClient.sql(createSql)
                        .then())
        .subscribe();

    }
}
