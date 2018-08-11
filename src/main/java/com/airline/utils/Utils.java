package com.airline.utils;

import com.airline.model.Ticket;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }


    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isDayOfWeek(String s) {
        return (s.equals("Sun") ||
                s.equals("Mon") ||
                s.equals("Tue") ||
                s.equals("Wed") ||
                s.equals("Thu") ||
                s.equals("Fri") ||
                s.equals("Sat"));
    }

    public static List<DayOfWeek> getDaysOfWeekByString(List<String> strings) {
        Map<String, DayOfWeek> mp = new HashMap<String, DayOfWeek>();

        mp.put("Sun", DayOfWeek.SUNDAY);
        mp.put("Mon", DayOfWeek.MONDAY);
        mp.put("Tue", DayOfWeek.TUESDAY);
        mp.put("Wed", DayOfWeek.WEDNESDAY);
        mp.put("Thu", DayOfWeek.THURSDAY);
        mp.put("Fri", DayOfWeek.FRIDAY);
        mp.put("Sat", DayOfWeek.SATURDAY);

        return strings
                .stream()
                .map(mp::get)
                .collect(Collectors.toList());
    }

    public static BigDecimal calculateTotalPrice(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticket -> ticket.getPrice().getPrice())
                .collect(Collectors.toList())
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
