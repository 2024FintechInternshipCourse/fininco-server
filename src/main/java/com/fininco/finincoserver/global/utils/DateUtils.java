package com.fininco.finincoserver.global.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateUtils {

    // 가장 최근 영업일
    public static LocalDate getLastBusinessDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // 토요일이면 금요일 return
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return date.minusDays(1);
        }
        // 일요일이면 금요일 return
        else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return date.minusDays(2);
        }
        // 주중이면 return
        else {
            return date;
        }
    }

    // 최근의 전날 영업일
    public static LocalDate getPreviousBusinessDay(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // 오늘이 월요일이면 금요일 return
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return date.minusDays(3);
        }
        // 오늘이 일요일이면 -> 오늘은 금요일, 최근은 목요일 return
        else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return date.minusDays(3);
        }
        // 오늘이 토요일이면 - > 오늘은 금요일, 최근은 목요일 return
        else if (dayOfWeek == DayOfWeek.SATURDAY) {
            return date.minusDays(2);
        }
        // 주중이면 그냥 전날기준으로 할 것
        else {
            return date.minusDays(1);
        }
    }
}