package com.cenacle.edge.swagger;

import com.cenacle.edge.constant.PharmacyOperatingStatus;
import com.cenacle.edge.model.GeoLocation;
import com.cenacle.edge.model.OpeningHoursDto;
import com.cenacle.edge.model.PharmacyAutocompleteItemDto;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.media.*;

import java.lang.reflect.Type;
import java.time.LocalTime;

public class PharmacySchemas {
    public static final String ID = "PharmacyId";
    public static final Schema<?> ID_SCHEMA = new StringSchema().title("약국 고유 Id").example("3fa85f6457174562b3fc2c963f66afa6");
    public static final String INSTITUTION_ID = "PharmacyInstitutionId";
    public static final Schema<?> INSTITUTION_ID_SCHEMA = new StringSchema().title("약국 기관 Id").example("C1101234");
    public static final String NAME = "PharmacyName";
    public static final Schema<?> NAME_SCHEMA = new StringSchema().title("약국 이름").example("세나약국");
    public static final String DISTANCE_IN_METER = "PharmacyDistanceInMeter";
    public static final Schema<?> DISTANCE_IN_METER_SCHEMA = new IntegerSchema().title("현위치로부터 거리(m)").example("35");
    public static final String ADDRESS = "PharmacyAddress";
    public static final Schema<?> ADDRESS_SCHEMA = new StringSchema().title("주소 정보").example("서울시 강남구 테헤란로 44길 8 6층");
    public static final String STREET_LEVEL_ADDRESS = "PharmacyStreetLevelAddress";
    public static final Schema<?> STREET_LEVEL_ADDRESS_SCHEMA = new StringSchema().title("도로 정보").example("서울시 강남구 테헤란로").description("약국의 도로명 주소의 도로명까지의 정보. 건물 번호는 나오지 않는다.");
    public static final String TODAY_STATUS = "PharmacyTodayStatus";
    public static final Schema<?> TODAY_STATUS_SCHEMA = getSchema(PharmacyOperatingStatus.class).title("오늘의 영업 상태");
    public static final String TODAY_BEGIN_TIME = "PharmacyTodayBeginTime";
    public static final Schema<?> TODAY_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("오늘의 영업 시작 시간").description("오늘의 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String TODAY_END_TIME = "PharmacyTodayEndTime";
    public static final Schema<?> TODAY_END_TIME_SCHEMA = getSchema(LocalTime.class).title("오늘의 영업 종료 시간").description("오늘의 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String TOMORROW_BEGIN_TIME = "PharmacyTomorrowBeginTime";
    public static final Schema<?> TOMORROW_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("내일의 영업 시작 시간").description("내일의 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String TOMORROW_END_TIME = "PharmacyTomorrowEndTime";
    public static final Schema<?> TOMORROW_END_TIME_SCHEMA = getSchema(LocalTime.class).title("내일의 영업 종료 시간").description("내일의 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String OPEN_TOMORROW = "PharmacyOpenTomorrow";
    public static final Schema<?> OPEN_TOMORROW_SCHEMA = new BooleanSchema().title("내일 영업 여부").example("true");
    public static final String OPEN_ON_HOLIDAYS = "PharmacyOpenOnHolidays";
    public static final Schema<?> OPEN_ON_HOLIDAYS_SCHEMA = new BooleanSchema().title("공휴일 영업 여부").example("false");
    public static final String OPENING_HOURS = "PharmacyOpeningHours";
    public static final Schema<?> OPENING_HOURS_SCHEMA = getSchema(OpeningHoursDto.class);
    public static final String FAVORITE = "PharmacyFavorite";
    public static final Schema<?> FAVORITE_SCHEMA = new BooleanSchema().title("즐겨찾기 여부").example("false");
    public static final String GEO_LOCATION = "PharmacyGeoLocation";
    public static final Schema<?> GEO_LOCATION_SCHEMA = getSchema(GeoLocation.class);
    public static final String PHONE_NUMBER = "PharmacyPhoneNumber";
    public static final Schema<?> PHONE_NUMBER_SCHEMA = new StringSchema().title("전화번호");
    public static final String AUTO_COMPLETE_FAVORITES = "PharmacyAutocompleteFavorites";
    public static final Schema<?> AUTO_COMPLETE_FAVORITES_SCHEMA = new ArraySchema().items(getSchema(PharmacyAutocompleteItemDto.class)).title("즐겨찾기 약국 목록").maxItems(3);
    public static final String AUTO_COMPLETE_SEARCH_RESULTS = "PharmacyAutocompleteSearchResults";
    public static final Schema<?> AUTO_COMPLETE_SEARCH_RESULTS_SCHEMA = new ArraySchema().items(getSchema(PharmacyAutocompleteItemDto.class)).title("약국 목록 검색 결과");
    public static final String CURRENTLY_OPEN = "PharmacyCurrentlyOpen";
    public static final Schema<?> CURRENTLY_OPEN_SCHEMA = new BooleanSchema().title("현재 영업 여부").example("true");
    public static final String LATE_NIGHT = "PharmacyLateNight";
    public static final Schema<?> LATE_NIGHT_SCHEMA = new BooleanSchema().title("야간 영업 여부").example("true");
    public static final String OPEN_YEAR_ROUND = "PharmacyOpenYearRound";
    public static final Schema<?> OPEN_YEAR_ROUND_SCHEMA = new BooleanSchema().title("연중무휴 여부").example("false");
    public static final String SEARCH_DATE_TIME = "PharmacySearchDateTime";
    public static final Schema<?> SEARCH_DATE_TIME_SCHEMA = new DateTimeSchema().title("검색 기준 시간");

    public static final String MON_BEGIN_TIME = "PharmacyMonBeginTime";
    public static final Schema<?> MON_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("월요일 영업 시작 시간").description("월요일 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String MON_END_TIME = "PharmacyMonEndTime";
    public static final Schema<?> MON_END_TIME_SCHEMA = getSchema(LocalTime.class).title("월요일 영업 종료 시간").description("월요일 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String TUE_BEGIN_TIME = "PharmacyTueBeginTime";
    public static final Schema<?> TUE_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("화요일 영업 시작 시간").description("화요일 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String TUE_END_TIME = "PharmacyTueEndTime";
    public static final Schema<?> TUE_END_TIME_SCHEMA = getSchema(LocalTime.class).title("화요일 영업 종료 시간").description("화요일 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String WED_BEGIN_TIME = "PharmacyWedBeginTime";
    public static final Schema<?> WED_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("수요일 영업 시작 시간").description("수요일 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String WED_END_TIME = "PharmacyWedEndTime";
    public static final Schema<?> WED_END_TIME_SCHEMA = getSchema(LocalTime.class).title("수요일 영업 종료 시간").description("수요일 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String THR_BEGIN_TIME = "PharmacyThrBeginTime";
    public static final Schema<?> THR_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("목요일 영업 시작 시간").description("목요일 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String THR_END_TIME = "PharmacyThrEndTime";
    public static final Schema<?> THR_END_TIME_SCHEMA = getSchema(LocalTime.class).title("목요일 영업 종료 시간").description("목요일 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String FRI_BEGIN_TIME = "PharmacyFriBeginTime";
    public static final Schema<?> FRI_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("금요일 영업 시작 시간").description("금요일 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String FRI_END_TIME = "PharmacyFriEndTime";
    public static final Schema<?> FRI_END_TIME_SCHEMA = getSchema(LocalTime.class).title("금요일 영업 종료 시간").description("금요일 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String SAT_BEGIN_TIME = "PharmacySatBeginTime";
    public static final Schema<?> SAT_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("토요일 영업 시작 시간").description("토요일 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String SAT_END_TIME = "PharmacySatEndTime";
    public static final Schema<?> SAT_END_TIME_SCHEMA = getSchema(LocalTime.class).title("토요일 영업 종료 시간").description("토요일 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String SUN_BEGIN_TIME = "PharmacySunBeginTime";
    public static final Schema<?> SUN_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("일요일 영업 시작 시간").description("일요일 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String SUN_END_TIME = "PharmacySunEndTime";
    public static final Schema<?> SUN_END_TIME_SCHEMA = getSchema(LocalTime.class).title("일요일 영업 종료 시간").description("일요일 영업 종료 시간. 영업하지 않을 경우 null 반환");
    public static final String HOLIDAY_BEGIN_TIME = "PharmacyHolidayBeginTime";
    public static final Schema<?> HOLIDAY_BEGIN_TIME_SCHEMA = getSchema(LocalTime.class).title("공휴일 영업 시작 시간").description("공휴일 영업 시작 시간. 영업하지 않을 경우 null 반환");
    public static final String HOLIDAY_END_TIME = "PharmacyHolidayEndTime";
    public static final Schema<?> HOLIDAY_END_TIME_SCHEMA = getSchema(LocalTime.class).title("공휴일 영업 종료 시간").description("공휴일 영업 종료 시간. 영업하지 않을 경우 null 반환");

   private PharmacySchemas() {
    }

    private static Schema<?> getSchema(Type type) {
        return ModelConverters.getInstance().readAllAsResolvedSchema(type).schema;
    }
}
