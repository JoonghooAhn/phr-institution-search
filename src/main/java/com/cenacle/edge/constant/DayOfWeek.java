package com.cenacle.edge.constant;

import com.cenacle.edge.support.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@AllArgsConstructor
public enum DayOfWeek {
    MON("MONDAY", "monBeginTime", "monEndTime"),
    TUE("TUESDAY", "tueBeginTime", "tueEndTime"),
    WED("WEDNESDAY", "wedBeginTime", "wedEndTime"),
    THU("THURSDAY", "thuBeginTime", "thuEndTime"),
    FRI("FRIDAY", "friBeginTime", "friEndTime"),
    SAT("SATURDAY", "satBeginTime", "satEndTime"),
    SUN("SUNDAY", "sunBeginTime", "sunEndTime"),
    HOLIDAY("HOLIDAY", "holidayBeginTime", "holidayEndTime");

    private String name;
    private String beginFieldName;
    private String endFieldName;

    public static DayOfWeek getTodayValue() {
        if (DateUtil.isHolidayToday()) {
            return HOLIDAY;
        }
        return fromDayOfWeek(LocalDate.now(ZoneId.of("Asia/Seoul")).getDayOfWeek());
    }

    public static DayOfWeek getTomorrowValue() {
        if (DateUtil.isHolidayTomorrow()) {
            return HOLIDAY;
        }
        return fromDayOfWeek(LocalDate.now(ZoneId.of("Asia/Seoul")).plusDays(1).getDayOfWeek());
    }

    private static DayOfWeek fromDayOfWeek(java.time.DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY -> {
                return MON;
            }
            case TUESDAY -> {
                return TUE;
            }
            case WEDNESDAY -> {
                return WED;
            }
            case THURSDAY -> {
                return THU;
            }
            case FRIDAY -> {
                return FRI;
            }
            case SATURDAY -> {
                return SAT;
            }
            case SUNDAY -> {
                return SUN;
            }
            default -> {
                return null;
            }
        }
    }
}
