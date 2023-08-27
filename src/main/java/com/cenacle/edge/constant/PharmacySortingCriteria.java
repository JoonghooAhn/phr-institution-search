package com.cenacle.edge.constant;

import com.cenacle.edge.model.GeoLocation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.GeoDistanceOrder;
import org.springframework.data.elasticsearch.core.query.Order;

@Schema(title = "약국 검색 정렬 기준 (PlaceSortingCriteria)")
public enum PharmacySortingCriteria {
    RELEVANT,       // 관련순
    PROXIMITY,      // 가까운순
    LATEST_CLOSING; // 영업종료 늦은순

    public Sort toSort(GeoLocation location) {
        switch (this) {
            case RELEVANT -> {
                return Sort.by(new Order(Sort.Direction.ASC, "name.keyword", Sort.NullHandling.NULLS_LAST));
            }
            case PROXIMITY -> {
                return location != null
                        ? Sort.by(new GeoDistanceOrder("geolocation", new GeoPoint(location.getLat(), location.getLon())).with(Sort.Direction.ASC).nullsLast())
                        : Sort.by(new Order(Sort.Direction.ASC, "name.keyword", Sort.NullHandling.NULLS_LAST));
            }
            case LATEST_CLOSING -> {
                return Sort.by(new Order(Sort.Direction.DESC, "openingHours.%s.keyword".formatted(DayOfWeek.getTodayValue().getEndFieldName()), Sort.NullHandling.NULLS_LAST));
            }
        }
        return Sort.unsorted();
    }
}
