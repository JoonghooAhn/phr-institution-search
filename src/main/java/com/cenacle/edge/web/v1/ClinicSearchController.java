package com.cenacle.edge.web.v1;

import com.cenacle.edge.model.ClinicAutocompleteDto;
import com.cenacle.edge.model.ClinicDto;
import com.cenacle.edge.model.ClinicSearchReqDto;
import com.cenacle.edge.model.GeoLocation;
import com.cenacle.edge.service.ClinicSearchService;
import com.cenacle.edge.support.user.CurrentUser;
import com.cenacle.edge.support.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping
@Tag(name = "PhrInstitutionSearch")
public class ClinicSearchController {

    private final ClinicSearchService clinicSearchService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "자동완성이 1건이상 정상적으로 검색 되었을 경우"),
            @ApiResponse(responseCode = "404", description = "필터링 조건에 해당하는 자동완성이 없을 경우")
    })
    @Operation(summary = "병원검색 키워드 자동완성 조회",
            description = "- nullable response\n" +
                    "  - distanceInMeter(병원과의 거리): 요청에 gps좌표가 있을때만 응답\n" +
                    "  - lastCareDateTime(마지막 진료일시): 다녀온 기록이 있을 경우에만 응답\n")
    @GetMapping(value = "/v1/clinics/search/autocomplete")
    public ClinicAutocompleteDto getClinicsForAutoComplete(@Parameter(description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
                                                           @Parameter(description = "현재 디바이스 GPS(위도)") @Nullable @RequestParam(required = false) Double latitude,
                                                           @Parameter(description = "현재 디바이스 GPS(경도)") @Nullable @RequestParam(required = false) Double longitude,
                                                           @Parameter(description = "검색어") @RequestParam(required = false) String keyword) {
        GeoLocation geoLocation = null;
        if (Objects.nonNull(latitude) && Objects.nonNull(longitude)) {
            geoLocation = new GeoLocation(latitude, longitude);
        }
        return clinicSearchService.searchAutocomplete(user, keyword, geoLocation);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "병원이 1건이상 정상적으로 검색 되었을 경우"),
            @ApiResponse(responseCode = "404", description = "필터링 조건에 해당하는 병원이 없을 경우")
    })
    @Operation(summary = "병원 검색",
            description = "- nullable response\n" +
                    "  - distanceInMeter(병원과의 거리): 요청에 gps좌표가 있을때만 응답\n" +
                    "  - expectedWaitingTime(진료대기 예상시간): '진료중' 상태일때만 예상시간 응답\n" +
                    "  - latestVisitInfo(다녀온 병원 마지막 방문정보): 다녀온 기록이 있을 경우에만 응답\n" +
                    "  - tags(태그): 태그가 있을 경우에만 응답\n" +
                    "  - clinicImageFileIds(병원이미지 파일id): 이미지가 등록되었을 경우에만 응답")
    @Parameters({
            @Parameter(name = "page", in = ParameterIn.QUERY, example = "0"),
            @Parameter(name = "size", in = ParameterIn.QUERY, example = "20")
    })
    @PostMapping(value = "/v1/clinics/search")
    public Slice<ClinicDto> getClinicsByKeyword(@Parameter(description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
                                                @Parameter(hidden = true) Pageable pageable,
                                                @Parameter(description = "검색 요청정보") @RequestBody ClinicSearchReqDto request) {
        return clinicSearchService.search(user, pageable, request);
    }

    // TODO: 병원 단건검색 friends API 필요해보임. (병원홈데이터를 위해)
}
