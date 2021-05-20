package ru.antonov.events.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtil {
    public static final String DATE_TIME_PATTERN = "dd-MM-dd HH:mm";
    public static final String LOCAL_TIME_PATTERN = "HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static LocalDateTime startDate() {
        return LocalDateTime.now()
                .toLocalDate()
                .atStartOfDay()
                .withDayOfMonth(1);
    }

    public static LocalDateTime endDate() {
        return LocalDateTime.now()
                .toLocalDate()
                .plusMonths(2)
                .withDayOfMonth(1)
                .minusDays(1)
                .atTime(23, 59, 59);
    }


    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static String toString(LocalTime lt) {
        return lt == null ? "" : lt.format(DateTimeFormatter.ofPattern(LOCAL_TIME_PATTERN));
    }

}
