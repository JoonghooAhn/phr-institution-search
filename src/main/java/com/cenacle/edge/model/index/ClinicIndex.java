package com.cenacle.edge.model.index;

import com.cenacle.edge.model.ClinicCareTimeDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "${cenacle.search.index-name}", createIndex = false)
@Data
@Schema(description = "병원검색 색인 정보")
@FieldNameConstants
public class ClinicIndex {
    @Id
    private String id;

    private String institutionId; // 기관id

    private String name; // 기관명

    private String medicalDepartmentCode; // 진료과코드 list

    private Boolean emergencyRoom; // 응급실운영여부

    private String description; // 기관설명상세

    private String address; // 주소

    private String roughAddress; // 간이약도

    private String zipCode; // 우편번호

    private String latitude; // 위도

    private String longitude; // 경도

//    private ClinicCareTimeDto careTime; // 진료시간

    private String phoneNumber; // 대표전화

    private String emergencyRoomPhoneNumber; // 응급실전화

    private Boolean goingOutOfBusiness; // 폐업여부

    private String workspaceId; // 병원ID

    private Integer distanceInMeter; // 병원과의 거리
}
