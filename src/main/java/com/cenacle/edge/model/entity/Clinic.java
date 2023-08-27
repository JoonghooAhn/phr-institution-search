package com.cenacle.edge.model.entity;

import com.cenacle.edge.model.ClinicCareTimeDto;
import com.cenacle.edge.support.jpa.AbstractBaseEntity;
import com.cenacle.edge.support.jpa.Domain;
import com.cenacle.edge.support.jpa.UuidKeyGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tb_clinic")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@GenericGenerator(name = "uuid", type = UuidKeyGenerator.class)
public class Clinic extends AbstractBaseEntity<String> {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(columnDefinition = "CHAR(32)", nullable = false)
    private String id;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String institutionId; // 기관id

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String name; // 기관명

    @Column(columnDefinition = "VARCHAR(255)")
    private String medicalDepartmentCode; // 진료과코드 list TODO: 진료과코드로 converting해야함

    @Column(columnDefinition = "bit(1) default b'0'")
    private Boolean emergencyRoom; // 응급실운영여부

    @Column(columnDefinition = "VARCHAR(4000)")
    private String description; // 기관설명상세

    @Column(columnDefinition = Domain.VC255)
    private String address; // 주소

    @Column(columnDefinition = Domain.VC255)
    private String roughAddress; // 간이약도

    @Column(columnDefinition = Domain.VC10)
    private String zipCode; // 우편번호

    @Column(columnDefinition = Domain.VC40)
    private String latitude; // 위도

    @Column(columnDefinition = Domain.VC40)
    private String longitude; // 경도

    @Column(columnDefinition = "JSON")
    @Convert(converter = ClinicCareTimeDto.Converter.class)
    private ClinicCareTimeDto careTime; // 진료시간

    @Column(columnDefinition = Domain.VC20)
    private String phoneNumber; // 대표전화

    @Column(columnDefinition = Domain.VC20)
    private String emergencyRoomPhoneNumber; // 응급실전화

    @Column(columnDefinition = "bit(1) default b'0'")
    private Boolean goingOutOfBusiness = false; // 폐업여부

    @Column(columnDefinition = "CHAR(32)")
    private String workspaceId; // 병원ID

    @Column(columnDefinition = "CHAR(32)")
    private String inadminId; // admin id

    @Column(columnDefinition = "JSON")
    private String originData; // 원본데이터

    @Column(columnDefinition = Domain.VC10)
    private String route; // 입력경로
}
