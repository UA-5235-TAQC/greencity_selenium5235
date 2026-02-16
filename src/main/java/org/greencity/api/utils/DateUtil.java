package org.greencity.api.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_TO_MINUTES_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static String getShortDate() {
        return ZonedDateTime.now(ZoneOffset.UTC).format(SHORT_DATE_FORMAT);
    }

    public static String getCurrentDateTimeToMinutes() {
        return ZonedDateTime.now(ZoneOffset.UTC).format(DATE_TIME_TO_MINUTES_FORMAT);
    }

    public static LocalDate parseShortDate(String date) {
        if (date.contains("T")) {
            return OffsetDateTime.parse(date).toLocalDate();
        }
        return LocalDate.parse(date, SHORT_DATE_FORMAT);
    }

    public static LocalDateTime parseDateTimeToMinutes(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_TO_MINUTES_FORMAT);
    }
}
