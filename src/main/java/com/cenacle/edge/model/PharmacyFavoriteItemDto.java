package com.cenacle.edge.model;

import com.cenacle.edge.constant.PharmacyOperatingStatus;
import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@Schema(title = "찜목록 약국 항목 Dto(PharmacyFavoriteItemDto)", description = "찜목록 약국 항목을 나타내는 클래스로, 약국의 기본 정보, 오늘 및 내일의 영업 상태, 영업 시간 등을 포함합니다.")
public class PharmacyFavoriteItemDto {
    @Schema(ref = PharmacySchemas.ID)
    private String id;

    @Schema(ref = PharmacySchemas.NAME)
    private String name;

    @Schema(ref = PharmacySchemas.DISTANCE_IN_METER)
    private Integer distanceInMeter;

    @Schema(ref = PharmacySchemas.STREET_LEVEL_ADDRESS)
    private String streetLevelAddress;

    @Schema(ref = PharmacySchemas.SEARCH_DATE_TIME)
    private ZonedDateTime searchDateTime;

    @Schema(ref = PharmacySchemas.TODAY_STATUS)
    private PharmacyOperatingStatus todayStatus;

    @Schema(ref = PharmacySchemas.TODAY_BEGIN_TIME)
    private LocalTime todayBeginTime;

    @Schema(ref = PharmacySchemas.TODAY_END_TIME)
    private LocalTime todayEndTime;

    @Schema(ref = PharmacySchemas.OPEN_TOMORROW)
    private Boolean openTomorrow;

    @Schema(ref = PharmacySchemas.TOMORROW_BEGIN_TIME)
    private LocalTime tomorrowBeginTime;

    @Schema(ref = PharmacySchemas.TOMORROW_END_TIME)
    private LocalTime tomorrowEndTime;
}
