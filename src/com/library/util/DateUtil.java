package com.library.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for handling date operations
 */
public class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Formats a LocalDate to string
     * @param date Date to format
     * @return Formatted date string
     */
    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Parses a date string to LocalDate
     * @param dateStr Date string to parse
     * @return LocalDate object
     * @throws DateTimeParseException if string cannot be parsed
     */
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }
    
    /**
     * Checks if a date string is valid
     * @param dateStr Date string to check
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateStr) {
        try {
            parseDate(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Calculates days between two dates
     * @param start Start date
     * @param end End date
     * @return Number of days between dates
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return java.time.temporal.ChronoUnit.DAYS.between(start, end);
    }
    
    /**
     * Checks if a date is in the past
     * @param date Date to check
     * @return true if date is before today, false otherwise
     */
    public static boolean isInPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Checks if a date is in the future
     * @param date Date to check
     * @return true if date is after today, false otherwise
     */
    public static boolean isInFuture(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
}
