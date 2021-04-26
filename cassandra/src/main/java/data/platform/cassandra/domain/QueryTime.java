package data.platform.cassandra.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class QueryTime {

    private String day;

    private LocalTime startOffSet;

    private LocalTime endOffSet;

    private static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<QueryTime> getQueryTimeSpan(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<QueryTime> queryDates = new ArrayList<>();

        Period period = Period.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());
        int spanDays = period.getDays();

        if (spanDays == 0) {
            QueryTime queryTime = new QueryTime();
            queryTime.setDay(dayFormatter.format(startDateTime));
            queryTime.setStartOffSet(startDateTime.withNano(LocalTime.MIN.getNano()).toLocalTime());
            queryTime.setEndOffSet(endDateTime.withNano(LocalTime.MAX.getNano()).toLocalTime());

            queryDates.add(queryTime);
        } else {
            // 第一天
            QueryTime firstDay = new QueryTime();
            firstDay.setDay(dayFormatter.format(startDateTime));
            firstDay.setStartOffSet(startDateTime.withNano(LocalTime.MIN.getNano()).toLocalTime());
            firstDay.setEndOffSet(LocalDateTime.of(startDateTime.toLocalDate(), LocalTime.MAX).toLocalTime());
            queryDates.add(firstDay);

            // 中间天数
            int middle = spanDays - 1;
            if (middle > 0) {
                for (int i = 0; i < middle; i++) {
                    LocalDateTime startTime = startDateTime.plusDays(i + 1);
                    QueryTime day = new QueryTime();
                    day.setDay(dayFormatter.format(startTime));
                    day.setStartOffSet(LocalDateTime.of(startTime.toLocalDate(), LocalTime.MIN).toLocalTime());
                    day.setEndOffSet(LocalDateTime.of(startTime.toLocalDate(), LocalTime.MAX).toLocalTime());
                    queryDates.add(day);
                }
            }
            // 最后一天
            QueryTime lastDay = new QueryTime();
            lastDay.setDay(dayFormatter.format(endDateTime));
            lastDay.setStartOffSet(LocalDateTime.of(endDateTime.toLocalDate(), LocalTime.MIN).toLocalTime());
            lastDay.setEndOffSet(endDateTime.withNano(LocalTime.MAX.getNano()).toLocalTime());
            queryDates.add(lastDay);
        }
        return queryDates;
    }

    public static void main(String[] args) {
        LocalDateTime beginTime = LocalDateTime.of(2021, 1, 26, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2021, 2, 1, 22, 9, 59);

        QueryTime.getQueryTimeSpan(beginTime, endTime).forEach(queryTime -> System.out.println(queryTime.toString()));
    }
}
