package com.cenacle.edge.model;

import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "약국 자동완성 검색 Dto(PharmacyAutocompleteRequestDto)")
public class PharmacyAutocompleteRequestDto {
    @Schema(title = "검색어", example = "세나")
    private String keyword;

    @Schema(ref = PharmacySchemas.GEO_LOCATION)
    private GeoLocation location;
}
