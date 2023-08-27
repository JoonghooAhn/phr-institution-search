package com.cenacle.edge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Schema(title = "병원정보(ClinicSimpleDto)")
public class ClinicSimpleDto {
    @Schema(title = "병원id")
    private String id;

    @Schema(title = "병원명")
    private String name;

    @Schema(title = "진료과")
//    private List<String> medicalDepartmentCodes;
    // TODO: List<String>으로 바꿔야함
    private String medicalDepartmentCodes;

    @Schema(title = "병원과의 거리(meter 단위)")
    private Integer distanceInMeter;

    @Schema(title = "마지막 진료일시")
    private ZonedDateTime lastCareDateTime;
}
