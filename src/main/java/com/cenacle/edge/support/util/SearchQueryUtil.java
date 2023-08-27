package com.cenacle.edge.support.util;

import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import com.cenacle.edge.constant.SortingCriteria;
import com.cenacle.edge.model.GeoLocation;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SearchQueryUtil {

    private static final String SCRIPT_LANGUAGE = "painless";

    /*
    병원과의 거리(distanceInMeter) 값을 계산하여 응답에 포함하기위하여 scriptData 형태로 반환한다.
     */
    public static ScriptData getScriptDataForDistanceInMeter(GeoLocation location) {
        if (Objects.isNull(location) || Objects.isNull(location.getLat()) || Objects.isNull(location.getLon())) {
            return null;
        }
        return new ScriptData(ScriptType.INLINE,
                SCRIPT_LANGUAGE,
                "doc['location'].arcDistance(params.latitude, params.longitude)",
                "distanceInMeter",
                Map.of("latitude", location.getLat(), "longitude", location.getLon()));
    }

    /*
    현재 위치와 주어진 반경 내에서 검색하도록 필터링하기위하여 geoDistanceQuery를 반환한다.
     */
    public static GeoDistanceQuery getGeoDistanceQuery(GeoLocation location, Integer radiusInMeters) {
        return new GeoDistanceQuery.Builder()
                .location(new co.elastic.clients.elasticsearch._types.GeoLocation.Builder()
                        .latlon(new LatLonGeoLocation.Builder()
                                .lat(location.getLat())
                                .lon(location.getLon())
                                .build())
                        .build())
                .distance(Optional.ofNullable(radiusInMeters).orElse(50).toString())
                .distanceType(GeoDistanceType.Plane)
                .field("location")
                .build();
    }

    public static Sort getSort(String property, Sort.Direction direction) {
        return Sort.by(
                new Order(direction, property)
        );
    }

    public static Sort getSortByGeoDistanceOrder(GeoLocation location, String property, Sort.Direction direction) {
        return Sort.by(
                new GeoDistanceOrder(property, new GeoPoint(location.getLat(), location.getLon()))
                        .with(direction)
        );
    }

    public static Sort toSort(SortingCriteria sort, GeoLocation location) {
        if (Objects.isNull(sort) || SortingCriteria.RELEVANT == sort) {
//            return Sort.by(new Order(Sort.Direction.ASC, "name.keyword", Sort.NullHandling.NULLS_LAST));
            // TODO: 위 테스트필요 es에서 관련도순으로 조회한 뒤 이름키워드로 sort필요한지? 가까운순 먼저 응답받고, 키워드가 같을경우 sort인지 확인필요
            return Sort.unsorted();
        }

        switch(sort) {
            case PROXIMITY :
                return Objects.isNull(location) ?
                        getSort("name.keyword", Sort.Direction.ASC) :
                        getSortByGeoDistanceOrder(location, "location", Sort.Direction.ASC);
            case LATEST_CLOSING : // TODO: 영업종료 늦은순 검토필요 (영업시간 없는곳은?)
            case RECENT_VISITED : // TODO: 최근 방문순 검토필요
            default :
                return Sort.unsorted();
        }
    }
}
