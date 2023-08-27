package com.cenacle.edge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "병원검색 요청정보(ClinicSearchReqDto)")
public class ClinicSearchReqDto {
    @Schema(title = "검색어")
    private String keyword;

    @Schema(title = "위치정보")
    private GeoLocation geoLocation;

    @Schema(title = "검색 할 반경(주변 검색 범위 / meter 단위) / default 50m")
    private Integer radiusInMeters;

    @Schema(title = "필터")
    private ClinicSearchFilterRequest filter;
}
