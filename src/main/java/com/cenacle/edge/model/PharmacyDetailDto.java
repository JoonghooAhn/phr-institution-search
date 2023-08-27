package com.cenacle.edge.model;

import com.cenacle.edge.constant.PharmacyOperatingStatus;
import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@FieldNameConstants
@Data
@Accessors(chain = true)
@Schema(title = "약국 상세 정보 Dto(PharmacyDetailDto)")
public class PharmacyDetailDto {
    @Schema(ref = PharmacySchemas.ID)
    private String id;

    @Schema(ref = PharmacySchemas.NAME)
    private String name;

    @Schema(ref = PharmacySchemas.DISTANCE_IN_METER)
    private Integer distanceInMeter;

    @Schema(ref = PharmacySchemas.STREET_LEVEL_ADDRESS)
    private String streetLevelAddress;

    @Schema(ref = PharmacySchemas.ADDRESS)
    private String address;

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

    @Schema(ref = PharmacySchemas.FAVORITE)
    private Boolean favorite;

    @Schema(ref = PharmacySchemas.GEO_LOCATION)
    private GeoLocation location;

    @Schema(ref = PharmacySchemas.PHONE_NUMBER)
    private String phoneNumber;

    @Schema(ref = PharmacySchemas.OPEN_ON_HOLIDAYS)
    private Boolean openHoliday;

    @Schema(ref = PharmacySchemas.OPENING_HOURS)
    private OpeningHoursDto openingHours;
}
