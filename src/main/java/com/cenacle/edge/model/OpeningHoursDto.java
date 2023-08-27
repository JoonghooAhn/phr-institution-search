package com.cenacle.edge.model;

import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

@Data
@Schema(title = "약국 영업시간 Dto(OpeningHoursDto)")
public class OpeningHoursDto {
    @Schema(ref = PharmacySchemas.MON_BEGIN_TIME)
    private LocalTime monBeginTime;

    @Schema(ref = PharmacySchemas.MON_END_TIME)
    private LocalTime monEndTime;

    @Schema(ref = PharmacySchemas.TUE_BEGIN_TIME)
    private LocalTime tueBeginTime;

    @Schema(ref = PharmacySchemas.TUE_END_TIME)
    private LocalTime tueEndTime;

    @Schema(ref = PharmacySchemas.WED_BEGIN_TIME)
    private LocalTime wedBeginTime;

    @Schema(ref = PharmacySchemas.WED_END_TIME)
    private LocalTime wedEndTime;

    @Schema(ref = PharmacySchemas.THR_BEGIN_TIME)
    private LocalTime thuBeginTime;

    @Schema(ref = PharmacySchemas.THR_END_TIME)
    private LocalTime thuEndTime;

    @Schema(ref = PharmacySchemas.FRI_BEGIN_TIME)
    private LocalTime friBeginTime;

    @Schema(ref = PharmacySchemas.FRI_END_TIME)
    private LocalTime friEndTime;

    @Schema(ref = PharmacySchemas.SAT_BEGIN_TIME)
    private LocalTime satBeginTime;

    @Schema(ref = PharmacySchemas.SAT_END_TIME)
    private LocalTime satEndTime;

    @Schema(ref = PharmacySchemas.SUN_BEGIN_TIME)
    private LocalTime sunBeginTime;

    @Schema(ref = PharmacySchemas.SUN_END_TIME)
    private LocalTime sunEndTime;

    @Schema(ref = PharmacySchemas.HOLIDAY_BEGIN_TIME)
    private LocalTime holidayBeginTime;

    @Schema(ref = PharmacySchemas.HOLIDAY_END_TIME)
    private LocalTime holidayEndTime;
}
