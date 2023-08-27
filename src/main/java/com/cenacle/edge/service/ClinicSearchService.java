package com.cenacle.edge.service;

import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import com.cenacle.edge.model.*;
import com.cenacle.edge.model.index.ClinicIndex;
import com.cenacle.edge.mapper.ClinicMapper;
import com.cenacle.edge.support.properties.SearchProperties;
import com.cenacle.edge.support.user.User;
import com.cenacle.edge.support.util.SearchQueryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ScriptType;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ClinicSearchService {

    private final ClinicFavoriteService clinicFavoriteService;
    private final SearchProperties searchProperties;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ClinicMapper clinicMapper;

    private static final Map<String, Float> NGRAM_MULTI_FIELDS_MAP = new HashMap<>() {{
        put(ClinicIndex.Fields.name, 0.5F);
        put(ClinicIndex.Fields.medicalDepartmentCode, 0.3F);
        put(ClinicIndex.Fields.description, 0.2F);
    }};

    public ClinicAutocompleteDto searchAutocomplete(User user, String keyword, GeoLocation location) {
        // TODO: 키워드 자동완성이 상위카테고리에 표시되면 하위에는 표시하지않는다. (우선순위: 찜목록 > 최근다녀온병원 > 검색결과)

        ClinicAutocompleteDto result = new ClinicAutocompleteDto();

        Query query = new CriteriaQuery(new Criteria("name").contains(keyword))
                .setPageable(Pageable.ofSize(10)); // 자동완성 10개
        NativeQueryBuilder builder = NativeQuery.builder().withQuery(query);

        Optional.ofNullable(location).ifPresentOrElse(
                e -> {
                    addDistanceInMeterField(builder, location);
                    query.addSort(SearchQueryUtil.getSortByGeoDistanceOrder(location, "location", Sort.Direction.ASC));
                },
                () -> query.addSort(SearchQueryUtil.getSort("name.keyword", Sort.Direction.ASC)));

        SearchHits<ClinicIndex> searchHits = elasticsearchTemplate.search(builder.build(),
                ClinicIndex.class,
                IndexCoordinates.of(searchProperties.getClinicIndexName()));

        List<ClinicSimpleDto> autocompleteItemDtos = searchHits.stream().map(e -> clinicMapper.toSimpleDto(e.getContent())).toList();
        if (CollectionUtils.isNotEmpty(autocompleteItemDtos)) {
            List<ClinicSimpleDto> favoriteClinicList = clinicFavoriteService.getSimpleList(user.getUserId());

            // TODO: 가져온 favorite 거리순 정렬
            // TODO: 거리순 top3을 autocompleteItemDtos에서 제외
        }

        // TODO: 최근 다녀온병원 set

        return result;
    }

    public Slice<ClinicDto> search(User user, Pageable pageable, ClinicSearchReqDto request) {
        Query query = new CriteriaQuery(new Criteria("name").contains(request.getKeyword()));
        NativeQueryBuilder builder = NativeQuery.builder().withQuery(query);

        Optional.ofNullable(request.getGeoLocation()).ifPresentOrElse(
                e -> {
                    setGeoRadiusFilter(builder, e, request.getRadiusInMeters());
                    addDistanceInMeterField(builder, e);
                    query.addSort(SearchQueryUtil.getSortByGeoDistanceOrder(e, "location", Sort.Direction.ASC));
                },
                () -> query.addSort(SearchQueryUtil.getSort("name.keyword", Sort.Direction.ASC)));

        builder.withPageable(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                SearchQueryUtil.toSort(request.getFilter().getSort(), request.getGeoLocation())));

        // TODO: request > filter 적용 필요

        return new SliceImpl<>(new ArrayList<>());
    }

    // 병원과의 거리(distanceInMeter) 값을 계산하여 응답에 포함
    private void addDistanceInMeterField(NativeQueryBuilder builder, GeoLocation location) {
        builder.withScriptedField(new ScriptedField("distanceInMeter", SearchQueryUtil.getScriptDataForDistanceInMeter(location)))
                .withSourceFilter(new FetchSourceFilterBuilder().build());
    }

    // 현재 위치와 주어진 반경 내에서 검색하도록 필터링
    private void setGeoRadiusFilter(NativeQueryBuilder builder, GeoLocation location, Integer radiusInMeter) {
        builder.withFilter(SearchQueryUtil.getGeoDistanceQuery(location, radiusInMeter)._toQuery());
    }
}


