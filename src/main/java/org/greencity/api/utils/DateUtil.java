package org.greencity.api.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseShortDate(String date) {
        if (date.contains("T")) {
            return OffsetDateTime.parse(date).toLocalDate();
        }
        return LocalDate.parse(date, SHORT_DATE_FORMAT);
    }

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        return parseShortDate(dateStr);
    }
}
