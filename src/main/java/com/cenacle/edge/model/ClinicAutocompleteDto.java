package com.cenacle.edge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "검색어 자동완성 결과")
public class ClinicAutocompleteDto {

    @Schema(title = "찜한 병원")
    private List<ClinicSimpleDto> favoriteClinics;

    @Schema(title = "최근 다녀온 병원")
    private List<ClinicSimpleDto> recentVisitedClinics;

    @Schema(title = "검색 결과로 필터링된 병원")
    private List<ClinicSimpleDto> matchedClinics;
}
