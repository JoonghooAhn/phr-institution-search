package com.cenacle.edge.model;

import com.cenacle.edge.support.jpa.GenericTypeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

@Data
@Schema(title = "병원 진료시간")
public class ClinicCareTimeDto {
    @Schema(title = "월요일 진료시작시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime monBeginTime;

    @Schema(title = "월요일 진료종료시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime monEndTime;

    @Schema(title = "화요일 진료시작시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tueBeginTime;

    @Schema(title = "화요일 진료종료시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tueEndTime;

    @Schema(title = "수요일 진료시작시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime wedBeginTime;

    @Schema(title = "수요일 진료종료시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime wedEndTime;

    @Schema(title = "목요일 진료시작시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime thuBeginTime;

    @Schema(title = "목요일 진료종료시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime thuEndTime;

    @Schema(title = "금요일 진료시작시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime friBeginTime;

    @Schema(title = "금요일 진료종료시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime friEndTime;

    @Schema(title = "토요일 진료시작시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime satBeginTime;

    @Schema(title = "토요일 진료종료시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime satEndTime;

    @Schema(title = "일요일 진료시작시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime sunBeginTime;

    @Schema(title = "일요일 진료종료시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime sunEndTime;

    @Schema(title = "공휴일 진료시작시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime holidayBeginTime;

    @Schema(title = "공휴일 진료종료시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime holidayEndTime;

    public static class Converter extends GenericTypeConverter<ClinicCareTimeDto> {
        public Converter() {
            super(new TypeReference<>() {});
        }
    }
}