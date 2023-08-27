package com.cenacle.edge.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ClinicCareState {
    // 진료전, 진료중, 휴식중, 진료종료, 휴무
    BEFORE_MEDICAL_CARE("BEFORE_MEDICAL_CARE", "진료전"),
    ONGOING_MEDICAL_CARE("ONGOING_MEDICAL_CARE", "진료중"),
    ON_BREAK("ON_BREAK", "휴식중"),
    FINISHED_MEDICAL_CARE("FINISHED_MEDICAL_CARE", "진료종료"),
    DAY_OFF("DAY_OFF", "휴무");

    private String code;
    private String name;

    public static ClinicCareState findByCode(String code) {
        return Arrays.stream(ClinicCareState.values()).filter(e -> StringUtils.equals(e.getCode(), code)).findAny().orElse(null);
    }
}
