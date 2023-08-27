package com.cenacle.edge.model;

import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "약국 검색 자동 완성 결과 Dto(PharmacyAutocompleteResultDto)")
public class PharmacyAutocompleteResultDto {
    @Schema(ref = PharmacySchemas.AUTO_COMPLETE_FAVORITES)
    private List<PharmacyAutocompleteItemDto> favoritePharmacies;

    @Schema(ref = PharmacySchemas.AUTO_COMPLETE_SEARCH_RESULTS)
    private List<PharmacyAutocompleteItemDto> matchedPharmacies;
}