package com.cenacle.edge.model;

import com.cenacle.edge.constant.ClinicCareState;
import com.cenacle.edge.constant.SortingCriteria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "필터정보")
public class ClinicSearchFilterRequest {
    @Schema(title = "진료과")
    private List<String> medicalDepartmentCodes;

    @Schema(title = "진료상태")
    private ClinicCareState medicalCareStatus;

    @Schema(title = "야간진료여부")
    private Boolean lateNightCare;

    @Schema(title = "연중무휴여부")
    private Boolean openYearRound;

    @Schema(title = "주차가능여부")
    private Boolean parkingSpaceAvailable;

    @Schema(title = "정렬기준")
    private SortingCriteria sort;
}
