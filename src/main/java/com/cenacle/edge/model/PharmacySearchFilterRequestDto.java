package com.cenacle.edge.model;

import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(title = "약국 필터 Dto(PharmacySearchFilterRequestDto)")
public class PharmacySearchFilterRequestDto {
    @Schema(ref = PharmacySchemas.CURRENTLY_OPEN)
    private Boolean currentlyOpen;
    @Schema(ref = PharmacySchemas.LATE_NIGHT)
    private Boolean lateNight;
    @Schema(ref = PharmacySchemas.OPEN_YEAR_ROUND)
    private Boolean openYearRound;
}
