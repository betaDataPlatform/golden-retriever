package data.platform.cassandra.config;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DefaultDriverOption;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.datastax.oss.driver.api.core.metadata.schema.KeyspaceMetadata;
import com.datastax.oss.driver.api.core.metadata.schema.TableMetadata;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.data.cassandra.core.cql.session.DefaultBridgedReactiveSession;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;

@Profile("cassandra")
@Configuration
@Slf4j
public class CassandraConfig {

    @Value("${cassandra.contact-points}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace-name}")
    private String keyspace;

    @Value("${cassandra.local-datacenter}")
    private String dataCenter;

    @Value("${cassandra.durable-writes}")
    private boolean durableWrites;

    // 表上列的最大保存时间
    @Value("${cassandra.defaultTimeToLive}")
    private int defaultTimeToLive;

    // 时间范围单位 MINUTES,HOURS,DAYS
    @Value("${cassandra.windowUnit}")
    private String windowUnit;

    // 时间长度，例如14天
    @Value("${cassandra.windowSize}")
    private int windowSize;

    // 多长时间把memetable刷新到sstable
    // 刷新由三个条件组成
    // memtable_flush_period_in_ms时间到
    // memetable容量达到指定容量的33%
    // commitlog达到指定容量的25%
    @Value("${cassandra.defaultMemtableFlushPeriodInMs}")
    private int defaultMemtableFlushPeriodInMs;

    public static final String CF_DATA_POINTS = "data_point";

    public static final String CF_METRIC_TAG = "metric_tag";

    @Bean
    public CqlSession cqlSession() {
        createKeySpace();

        DriverConfigLoader loader =
                DriverConfigLoader.programmaticBuilder()
                        .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(5))
                        .startProfile("slow")
                        .withDuration(DefaultDriverOption.REQUEST_TIMEOUT, Duration.ofSeconds(30))
                        .endProfile()
                        .build();

        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoints, port))
                .withKeyspace(keyspace)
                .withLocalDatacenter(dataCenter)
                .withConfigLoader(loader)
                .build();

        createDataPointTable(session);
        createMetricTagTable(session);

        return session;
    }

    @Bean
    public ReactiveCassandraOperations reactiveCassandraOperations() {
        return new ReactiveCassandraTemplate(new DefaultBridgedReactiveSession(cqlSession()));
    }

    private void createKeySpace() {
        CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoints, port))
                .withLocalDatacenter(dataCenter)
                .build();
        Optional<KeyspaceMetadata> keyspaceMetadataOptional = session.getMetadata().getKeyspace(keyspace);
        if (!keyspaceMetadataOptional.isPresent()) {
            CreateKeyspace createKeyspace = SchemaBuilder.createKeyspace(keyspace)
                    .withReplicationOptions(ImmutableMap.of("class", "SimpleStrategy", "replication_factor", 1))
                    .withDurableWrites(durableWrites);
            String createSql = createKeyspace.asCql();
            session.execute(createSql);
        } else if (keyspaceMetadataOptional.get().isDurableWrites() != durableWrites) {
            String updateSql = SchemaBuilder.alterKeyspace(keyspace).withDurableWrites(durableWrites).asCql();
            session.execute(updateSql);
        }
        session.close();
    }

    private void createDataPointTable(CqlSession session) {
        KeyspaceMetadata keyspaceMetadata = session.getMetadata().getKeyspace(keyspace).get();
        Optional<TableMetadata> tableMetadataOptional = keyspaceMetadata.getTable(CF_DATA_POINTS);
        if (!tableMetadataOptional.isPresent()) {
            try {
                String ddl = Resources.toString(Resources.getResource("cassandra/ddl-schema.cql"), Charsets.UTF_8);
                String[] sqlArr = ddl.split(";");
                session.execute(sqlArr[0]);
            } catch (Exception ex) {
                log.error("", ex);
            }
        } else {
            TableMetadata tableMetadata = tableMetadataOptional.get();
            boolean isChange = false;

            int tableDefaultTimeToLive = (Integer)tableMetadata.getOptions().get(CqlIdentifier.fromCql("default_time_to_live"));
            if (tableDefaultTimeToLive != defaultTimeToLive) {
                isChange = true;
            }
            if (!isChange) {
                int tableMemtableFlushPeriodInMs =(Integer)tableMetadata.getOptions().get(CqlIdentifier.fromCql("memtable_flush_period_in_ms"));
                if (tableMemtableFlushPeriodInMs != defaultMemtableFlushPeriodInMs) {
                    isChange = true;
                }
            }
            Map<String,Object> compactionMap = (Map<String, Object>) tableMetadata.getOptions().get(CqlIdentifier.fromCql("compaction"));
            if (!isChange) {
                if (compactionMap.containsKey("compaction_window_unit")) {
                    String tableWindowUnit = compactionMap.get("compaction_window_unit").toString();
                    log.info("compaction_window_unit: " + windowUnit + "," + tableWindowUnit);
                    if (!tableWindowUnit.equals(windowUnit)) {
                        isChange = true;
                    }
                }
            }
            if (!isChange) {
                if (compactionMap.containsKey("compaction_window_size")) {
                    int tableWindowSize = Integer.parseInt(compactionMap.get("compaction_window_size").toString());
                    log.info("compaction_window_size: " + windowSize + "," + tableWindowSize);
                    if (windowSize != tableWindowSize) {
                        isChange = true;
                    }
                }
            }
            if(isChange) {
                StringBuilder dataPointSb = new StringBuilder();
                dataPointSb.append("ALTER TABLE ").append(CF_DATA_POINTS)
                        .append(" WITH default_time_to_live = ").append(defaultTimeToLive)
                        .append(" AND gc_grace_seconds = ").append(60)
                        .append(" AND memtable_flush_period_in_ms = ").append(defaultMemtableFlushPeriodInMs)
                        .append(" AND compaction= {")
                        .append("'compaction_window_unit': '").append(windowUnit).append("',")
                        .append("'compaction_window_size': '").append(windowSize).append("',")
                        .append("'timestamp_resolution': 'MILLISECONDS',")
                        .append("'tombstone_compaction_interval': 60,")
                        .append("'tombstone_threshold': 0.2,")
                        .append("'expired_sstable_check_frequency_seconds':600,")
                        .append("'unchecked_tombstone_compaction':true,")
                        .append("'class':'org.apache.cassandra.db.compaction.TimeWindowCompactionStrategy'}");
                session.execute(dataPointSb.toString());
            }


        }
    }

    private void createMetricTagTable(CqlSession session) {
        KeyspaceMetadata keyspaceMetadata = session.getMetadata().getKeyspace(keyspace).get();
        Optional<TableMetadata> tableMetadataOptional = keyspaceMetadata.getTable(CF_METRIC_TAG);
        if (!tableMetadataOptional.isPresent()) {
            try {
                String ddl = Resources.toString(Resources.getResource("cassandra/ddl-schema.cql"), Charsets.UTF_8);
                String[] sqlArr = ddl.split(";");
                session.execute(sqlArr[1]);
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

}
