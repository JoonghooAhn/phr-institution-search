package com.cenacle.edge.model;

import com.cenacle.edge.constant.ClinicCareState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(title = "병원정보(ClinicDto)")
public class ClinicDto {
    @Schema(title = "id")
    private String id;

    @Schema(title = "병원명")
    private String name;

    @Schema(title = "진료과")
    private List<String> medicalDepartmentCodes;

    @Schema(title = "주소")
    private String address;

    @Schema(title = "위치정보")
    private GeoLocation geoLocation;

    @Schema(title = "병원과의 거리(meter 단위)")
    private Integer distanceInMeter;

    @Schema(title = "대표전화번호")
    private String phoneNumber;

    @Schema(title = "진료상태")
    private ClinicCareState careStatus;

    @Schema(title = "당일 병원 스케쥴 정보")
    private TodayClinicScheduleInfoDto todayClinicScheduleInfo;

    @Schema(title = "진료대기 예상시간(분)")
    private Integer expectedWaitingTime; // TODO: [병원] 예상대기시간은 '진료중'인 경우만 노출

    @Schema(title = "다녀온병원 여부")
    private Boolean visited;

    @Schema(title = "오름차트 사용병원 여부")
    private Boolean useOrmChart;

    @Schema(title = "다녀온 병원 마지막 방문정보")
    private LatestVisitInfoDto latestVisitInfo;

    @Schema(title = "PHR 예약 가능 여부")
    private Boolean availableReservation;

    @Schema(title = "PHR 접수 가능 여부")
    private Boolean availableReception;

    @Schema(title = "PHR 자동결제 가능 여부")
    private Boolean availablePay;

    @Schema(title = "찜한병원 여부")
    private Boolean favorite;

    @Schema(title = "태그리스트")
    private List<String> tags;

    @Schema(title = "병원 사진 fileIds")
    private List<String> clinicImageFileIds;
}
