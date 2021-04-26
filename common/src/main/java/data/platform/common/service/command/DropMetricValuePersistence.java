package data.platform.common.service.command;

import data.platform.common.event.DropMetricValueEvent;
import data.platform.common.protobuf.MetricValueProto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
public class DropMetricValuePersistence {

    private Path backupPath;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");

    private BlockingQueue<DropMetricValueEvent> queue;

    private AtomicBoolean bufIsFull = new AtomicBoolean(false);

    @PostConstruct
    public void init() {
        try {
            queue = new ArrayBlockingQueue<>(1000);
            backupPath = Paths.get("metricBackup");
            if (Files.notExists(backupPath)) {
                Files.createDirectory(backupPath);
            }

            Thread backupThread = new Thread(() -> {
                saveToDisk();
            });
            backupThread.setName("metricValue-backup");
            backupThread.start();

        } catch (Exception ex) {
            log.error("", ex);
        }
    }

    public void addDropEvent(DropMetricValueEvent event) {
        if (!queue.offer(event)) {
            if (bufIsFull.compareAndSet(false, true)) {
                log.error("drop metricValue queue size is full.......");
            }
        } else if (bufIsFull.compareAndSet(true, false)) {
            log.error("drop metricValue queue size back to normal.......");
        }
    }

    /**
     * // reading
     * try (FileInputStream input = new FileInputStream(path)) {
     * while (true) {
     * Person person = Person.parseDelimitedFrom(input);
     * if (person == null) { // parseDelimitedFrom returns null on EOF
     * break;
     * }
     * }
     */
    public void saveToDisk() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                List<DropMetricValueEvent> metricValues = new ArrayList<>();
                metricValues.add(queue.take());
                queue.drainTo(metricValues);

                Path backupFile = backupPath.resolve(formatter.format(LocalDateTime.now()));
                if(Files.notExists(backupPath)) {
                    Files.createFile(backupFile);
                }

                try (FileOutputStream output = new FileOutputStream(backupFile.toFile(), true)) {
                    for (DropMetricValueEvent metricValue : metricValues) {
                        MetricValueProto metricValueProto = DropMetricValueEvent.toMetricValueProto(metricValue);
                        metricValueProto.writeDelimitedTo(output);
                    }
                }
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }
}
