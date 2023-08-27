package com.cenacle.edge.constant;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

@Schema(title = "약국의 현재 영업 상태 (PharmacyOperatingStatus)")
public enum PharmacyOperatingStatus {
    PRE_OPENING,    // 영업 전
    OPEN,           // 영업중
    CLOSED,         // 영업 종료
    DAY_OFF;         // 휴무일

    public static PharmacyOperatingStatus getStatus(LocalTime time, LocalTime beginTime, LocalTime endTime) {
        if (time == null) {
            return null;
        }
        if (beginTime == null || endTime == null) {
            return DAY_OFF;
        }
        if (time.isBefore(beginTime)) {
            return PRE_OPENING;
        }
        if (time.isAfter(endTime)) {
            return CLOSED;
        }
        if (time.isAfter(beginTime) && time.isBefore(endTime)) {
            return OPEN;
        }
        return null;
    }
}
