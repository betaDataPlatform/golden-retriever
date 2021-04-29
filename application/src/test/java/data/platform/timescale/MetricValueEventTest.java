package data.platform.timescale;

import data.platform.common.domain.MetricValue;
import data.platform.common.event.MetricValueEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@ActiveProfiles("timescale")
public class MetricValueEventTest {


    @Autowired
    ApplicationContext applicationContext;

    @Test
    void save() throws InterruptedException {
        List<Map<String, String>> tagList = new ArrayList<>();
        tagList.add(createTag("30.0.0.1","sh","r01"));
        tagList.add(createTag("30.0.0.2","sh","r01"));
        tagList.add(createTag("30.0.0.3","sh","r02"));
        tagList.add(createTag("10.0.0.1","bj","r01"));
        tagList.add(createTag("10.0.0.2","bj","r02"));

        int valueSize = 1;
        for(Map<String, String> tag : tagList) {
            for (int i = 0; i < 10; i++) {
                MetricValue metricValue = new MetricValue();
                metricValue.setMetric("CPU_LOAD");
                metricValue.setTag(tag);

                metricValue.setEventTime(LocalDateTime.now().plusMinutes(i));
                metricValue.setValue(Double.valueOf(valueSize)*100);
                metricValue.setTtl(new Integer(0));

                valueSize ++;
                applicationContext.publishEvent(new MetricValueEvent(metricValue));
            }
        }
        Thread.sleep(60 * 1000);
    }

    private Map<String, String> createTag(String ip, String location, String room) {
        Map<String, String> tags = new HashMap<>();
        tags.put("host", ip);
        tags.put("location", location);
        tags.put("room", room);
        return tags;
    }

}
