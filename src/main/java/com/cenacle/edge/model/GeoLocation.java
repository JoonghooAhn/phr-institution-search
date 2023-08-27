package com.cenacle.edge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
@Schema(title = "위치 정보 (Geolocation)")
public class GeoLocation {
    @Schema(title = "위도", example = "37.5023181")
    private Double lat;

    @Schema(title = "경도", example = "127.0444361")
    private Double lon;
}
