package fr.limayrac.poubelle.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static String formatDateTime(String format, LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    public static boolean isBetweenOrEquals(LocalDateTime start, LocalDateTime actual, LocalDateTime end) {
        return (start.isBefore(actual) || start.isEqual(actual))
                && (end.isAfter(actual) || end.isEqual(actual));
    }

    public static boolean isWinter() {
        LocalDateTime today = LocalDateTime.now();
        if (today.getMonth() == Month.DECEMBER) {
            // Si on se situe après le 21, on est en hiver
            return today.getDayOfMonth() >= 21;
        } else {
            // De janvier à Mars on est en hiver
            if (today.getMonth().getValue() <= Month.MARCH.getValue()) {
                // Après le 20 Mars, on est au printemps
                if (today.getMonth() == Month.MARCH) {
                    return today.getDayOfMonth() <= 20;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }
}
