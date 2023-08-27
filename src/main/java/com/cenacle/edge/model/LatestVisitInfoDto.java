package com.cenacle.edge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Schema(title = "마지막 방문정보(LatestVisitInfoDto)")
public class LatestVisitInfoDto {
    @Schema(title = "병원id")
    private String id;

    @Schema(title = "병원명")
    private String name;

    @Schema(title = "다녀온 유저정보")
    private List<UserDto> users;

    @Schema(title = "진료일시")
    private ZonedDateTime careDateTime;
}
