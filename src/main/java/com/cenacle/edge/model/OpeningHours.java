package com.cenacle.edge.model;

import com.cenacle.edge.constant.DayOfWeek;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class OpeningHours {
    @Schema(title = "월요일 영업시작시간")
    private String monBeginTime;

    @Schema(title = "월요일 영업종료시간")
    private String monEndTime;

    @Schema(title = "화요일 영업시작시간")
    private String tueBeginTime;

    @Schema(title = "화요일 영업종료시간")
    private String tueEndTime;

    @Schema(title = "수요일 영업시작시간")
    private String wedBeginTime;

    @Schema(title = "수요일 영업종료시간")
    private String wedEndTime;

    @Schema(title = "목요일 영업시작시간")
    private String thuBeginTime;

    @Schema(title = "목요일 영업종료시간")
    private String thuEndTime;

    @Schema(title = "금요일 영업시작시간")
    private String friBeginTime;

    @Schema(title = "금요일 영업종료시간")
    private String friEndTime;

    @Schema(title = "토요일 영업시작시간")
    private String satBeginTime;

    @Schema(title = "토요일 영업종료시간")
    private String satEndTime;

    @Schema(title = "일요일 영업시작시간")
    private String sunBeginTime;

    @Schema(title = "일요일 영업종료시간")
    private String sunEndTime;

    @Schema(title = "공휴일 영업시작시간")
    private String holidayBeginTime;

    @Schema(title = "공휴일 영업종료시간")
    private String holidayEndTime;

    public String getBeginTime(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MON -> {
                return getMonBeginTime();
            }
            case TUE -> {
                return getTueBeginTime();
            }
            case WED -> {
                return getWedBeginTime();
            }
            case THU -> {
                return getThuBeginTime();
            }
            case FRI -> {
                return getFriBeginTime();
            }
            case SAT -> {
                return getSatBeginTime();
            }
            case SUN -> {
                return getSunBeginTime();
            }
            case HOLIDAY -> {
                return getHolidayBeginTime();
            }
        }
        return null;
    }
    public String getEndTime(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MON -> {
                return getMonEndTime();
            }
            case TUE -> {
                return getTueEndTime();
            }
            case WED -> {
                return getWedEndTime();
            }
            case THU -> {
                return getThuEndTime();
            }
            case FRI -> {
                return getFriEndTime();
            }
            case SAT -> {
                return getSatEndTime();
            }
            case SUN -> {
                return getSunEndTime();
            }
            case HOLIDAY -> {
                return getHolidayEndTime();
            }
        }
        return null;
    }
}