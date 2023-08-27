package com.cenacle.edge.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@Schema(title = "병원 스케쥴 정보(TodayClinicScheduleInfoDto)")
public class TodayClinicScheduleInfoDto {

    @Schema(title = "조회기준일시")
    private ZonedDateTime searchDateTime;

    @Schema(title = "금일 진료 시작 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime todayBeginTime;

    @Schema(title = "금일 진료 종료 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime todayEndTime;

    @Schema(title = "명일 진료여부")
    private Boolean openTomorrow;

    @Schema(title = "명일 진료 종료 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tomorrowBeginTime;

    @Schema(title = "명일 진료 종료 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tomorrowEndTime;
}
