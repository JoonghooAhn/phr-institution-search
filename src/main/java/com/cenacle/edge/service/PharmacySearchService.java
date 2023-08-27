package com.cenacle.edge.service;

import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import com.cenacle.edge.constant.DayOfWeek;
import com.cenacle.edge.constant.PharmacySortingCriteria;
import com.cenacle.edge.jpa.PharmacyFavoriteSpecs;
import com.cenacle.edge.mapper.PharmacyMapper;
import com.cenacle.edge.model.*;
import com.cenacle.edge.model.entity.PharmacyFavorite;
import com.cenacle.edge.model.index.PharmacyIndex;
import com.cenacle.edge.repository.PharmacyFavoriteRepository;
import com.cenacle.edge.support.properties.SearchProperties;
import com.cenacle.edge.support.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacySearchService {
    private final SearchProperties searchProperties;
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final PharmacyFavoriteRepository pharmacyFavoriteRepository;
    private final PharmacyMapper pharmacyMapper;

    public PharmacyAutocompleteResultDto searchPharmacyAutocomplete(String userId, PharmacyAutocompleteRequestDto request) {
        Criteria criteria = new Criteria("name").contains(request.getKeyword());
        Query query = new CriteriaQuery(criteria)
                .setPageable(Pageable.ofSize(10));

        NativeQueryBuilder builder = NativeQuery.builder()
                .withQuery(query);
        GeoLocation location = request.getLocation();
        if (request.getLocation() != null) {
            builder.withScriptedField(new ScriptedField("distanceInMeter",
                            new ScriptData(ScriptType.INLINE,
                                    "painless",
                                    "doc['location'].arcDistance(params.lat, params.lon)",
                                    null,
                                    Map.of("lat", location.getLat(), "lon", location.getLon()))))
                    .withSourceFilter(new FetchSourceFilterBuilder().withExcludes().build());
            query.addSort(Sort.by(new GeoDistanceOrder("location", new GeoPoint(location.getLat(), location.getLon())).with(Sort.Direction.ASC)));
        } else {
            query.addSort(Sort.by(new Order(Sort.Direction.ASC, "name.keyword")));
        }

        SearchHits<PharmacyIndex> searchHits = elasticsearchTemplate.search(builder.build(), PharmacyIndex.class, IndexCoordinates.of(searchProperties.getPharmacyIndexName()));
        List<PharmacyAutocompleteItemDto> autocompleteItemDtos = searchHits.stream().map(hit -> pharmacyMapper.toAutocompleteItemDto(hit.getContent())).toList();
        List<String> pharmacyIds = autocompleteItemDtos.stream().map(PharmacyAutocompleteItemDto::getId).toList();
        List<String> favoriteIds = pharmacyFavoriteRepository.findAll(PharmacyFavoriteSpecs.withDeleted(false)
                        .and(PharmacyFavoriteSpecs.withUserId(userId))
                        .and(PharmacyFavoriteSpecs.withPharmacyIdIn(pharmacyIds)))
                .stream()
                .map(PharmacyFavorite::getId)
                .toList();

        List<PharmacyAutocompleteItemDto> favoritePharmacies = new ArrayList<>();
        List<PharmacyAutocompleteItemDto> matchedPharmacies = new ArrayList<>();

        autocompleteItemDtos.forEach(item -> {
            if (favoriteIds.contains(item.getId())) {
                if (favoritePharmacies.size() < 3) {
                    favoritePharmacies.add(item);
                }
            } else {
                matchedPharmacies.add(item);
            }
        });
        PharmacyAutocompleteResultDto resultDto = new PharmacyAutocompleteResultDto();
        resultDto.setFavoritePharmacies(favoritePharmacies);
        resultDto.setMatchedPharmacies(matchedPharmacies);

        return resultDto;
    }

    public Slice<PharmacyDetailDto> searchPharmacy(User user, PharmacySearchRequestDto request, Pageable pageable) {
        CriteriaQuery query = new CriteriaQuery(new Criteria("name").contains(request.getKeyword()));

        NativeQueryBuilder builder = NativeQuery.builder();

        GeoLocation location = request.getLocation();
        if (request.getLocation() != null) {
            builder.withScriptedField(new ScriptedField("distanceInMeter",
                            new ScriptData(ScriptType.INLINE,
                                    "painless",
                                    "doc['location'].arcDistance(params.lat, params.lon)",
                                    null,
                                    Map.of("lat", location.getLat(), "lon", location.getLon()))))
                    .withSourceFilter(new FetchSourceFilterBuilder().withExcludes().build());
            GeoDistanceQuery geoFilter = new GeoDistanceQuery.Builder()
                    .location(new co.elastic.clients.elasticsearch._types.GeoLocation.Builder()
                            .latlon(new LatLonGeoLocation.Builder()
                                    .lat(location.getLat())
                                    .lon(location.getLon())
                                    .build())
                            .build())
                    .distance(Optional.ofNullable(request.getRadiusInMeters()).orElse(50).toString())
                    .distanceType(GeoDistanceType.Plane)
                    .field("location")
                    .build();
            builder.withFilter(geoFilter._toQuery());
        }

        PharmacySortingCriteria sortingCriteria = Optional.ofNullable(request.getSort())
                .orElse(PharmacySortingCriteria.RELEVANT);
        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortingCriteria.toSort(location));
        builder.withPageable(pageRequest);

        Optional.ofNullable(request.getFilter()).ifPresent(filter -> {
            if (Boolean.TRUE.equals(filter.getCurrentlyOpen())) {
                LocalTime now = LocalTime.now(ZoneId.of("Asia/Seoul"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                // 오늘 영업하는지 확인 && 지금 시간이 시작 시간보다 크고 && 지금 시간이 종료 시간보다 작은지 확인
                query.addCriteria(new Criteria("openingHours.%s.keyword".formatted(DayOfWeek.getTodayValue().getBeginFieldName()))
                        .exists()
                        .lessThanEqual(now.format(formatter))
                        .and("openingHours.%s.keyword".formatted(DayOfWeek.getTodayValue().getEndFieldName()))
                        .exists()
                        .greaterThanEqual(now.format(formatter)));
            }
            if (Boolean.TRUE.equals(filter.getLateNight())) {
                // 오늘 영업하는지 확인 && 종료 시간이 19:00:00보다 작은지 확인
                query.addCriteria(new Criteria("openingHours.%s.keyword".formatted(DayOfWeek.getTodayValue().getEndFieldName()))
                        .exists()
                        .greaterThanEqual("19:00"));
            }
            if (Boolean.TRUE.equals(filter.getOpenYearRound())) {
                Arrays.stream(DayOfWeek.values())
                        .map(DayOfWeek::getBeginFieldName)
                        .map("openingHours.%s"::formatted)
                        .map(Criteria::new)
                        .map(Criteria::exists)
                        .forEach(query::addCriteria);
            }
        });

        builder.withQuery(query);

        ZonedDateTime searchDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        SearchHits<PharmacyIndex> searchHits = elasticsearchTemplate.search(builder.build(), PharmacyIndex.class, IndexCoordinates.of(searchProperties.getPharmacyIndexName()));
        SearchPage<PharmacyIndex> searchPage = SearchHitSupport.searchPageFor(searchHits, pageRequest);
        List<String> pharmacyIds = searchPage.map(SearchHit::getContent)
                .map(PharmacyIndex::getId)
                .toList();

        List<String> favoritePharmacyIds = pharmacyFavoriteRepository.findAll(PharmacyFavoriteSpecs.withDeleted(false)
                        .and(PharmacyFavoriteSpecs.withUserId(user.getUserId()))
                        .and(PharmacyFavoriteSpecs.withPharmacyIdIn(pharmacyIds)))
                .stream()
                .map(PharmacyFavorite::getPharmacyId)
                .toList();

        return searchPage.map(SearchHit::getContent)
                .map(index -> pharmacyMapper.toDetailDto(index)
                        .setSearchDateTime(searchDateTime)
                        .setFavorite(favoritePharmacyIds.contains(index.getId())));
    }
}
