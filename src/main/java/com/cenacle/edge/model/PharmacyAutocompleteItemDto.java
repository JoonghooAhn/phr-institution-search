package com.cenacle.edge.model;

import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "약국 검색 자동 완성 항목 Dto(PharmacyAutoCompleteDto)")
public class PharmacyAutocompleteItemDto {
    @Schema(ref = PharmacySchemas.ID)
    private String id;

    @Schema(ref = PharmacySchemas.NAME)
    private String name;

    @Schema(ref = PharmacySchemas.DISTANCE_IN_METER)
    private Integer distanceInMeter;
}
