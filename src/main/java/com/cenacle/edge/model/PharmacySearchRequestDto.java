package com.cenacle.edge.model;

import com.cenacle.edge.constant.PharmacySortingCriteria;
import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "약국 검색 Dto(PharmacyMapSearchRequestDto)")
public class PharmacySearchRequestDto {
    @Schema(title = "검색어", example = "세나")
    private String keyword;
    @Schema(ref = PharmacySchemas.GEO_LOCATION)
    private GeoLocation location;
    @Schema(title = "검색 반경", description = "검색 반경. meter 단위, 기본값 50m", example = "50")
    private Integer radiusInMeters;
    @Schema(title = "필터")
    private PharmacySearchFilterRequestDto filter;
    @Schema(title = "정렬 기준")
    private PharmacySortingCriteria sort;

    public String getKeyword() {
        return keyword;
    }
}
