package com.cenacle.edge.web.v1;

import com.cenacle.edge.model.*;
import com.cenacle.edge.service.PharmacyFavoriteService;
import com.cenacle.edge.service.PharmacySearchService;
import com.cenacle.edge.service.PharmacyService;
import com.cenacle.edge.support.user.CurrentUser;
import com.cenacle.edge.support.user.User;
import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("v1/pharmacies")
@Slf4j
public class PharmacySearchController {

    private final PharmacySearchService pharmacySearchService;
    private final PharmacyFavoriteService pharmacyFavoriteService;
    private final PharmacyService pharmacyService;

    @Tag(name = "PharmacySearch", description = "약국 조회")
    @Operation(summary = "키워드 자동완성", description = "사용자가 입력한 키워드에 해당하는 약국 목록을 자동완성 형태로 제공합니다. 검색 결과가 없는 경우 빈 목록을 반환합니다.")
    @ApiResponse(responseCode = "200")
    @PostMapping(path = "/search/autocomplete")
    public PharmacyAutocompleteResultDto getPharmacyAutocomplete(
            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "검색 요청정보") @RequestBody PharmacyAutocompleteRequestDto request) {
        return pharmacySearchService.searchPharmacyAutocomplete(user.getUserId(), request);
    }

    @Tag(name = "PharmacySearch", description = "약국 조회")
    @Operation(summary = "약국 검색", description = "검색 키워드와 필터 조건을 기반으로 약국 목록을 반환합니다.")
    @ApiResponse(responseCode = "200")
    @PostMapping(path = "/search")
    public Slice<PharmacyDetailDto> getPharmacySearch(
            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "검색 요청정보") @RequestBody PharmacySearchRequestDto request,
            @Parameter(in = ParameterIn.QUERY, hidden = true) @PageableDefault(size = 30, sort = "RELEVANT") Pageable pageable
    ) {
        return pharmacySearchService.searchPharmacy(user, request, pageable);
    }

//    @Tag(name = "PharmacySearch", description = "약국 조회")
//    @Operation(summary = "약국 검색", description = "검색 키워드와 필터 조건을 기반으로 약국 목록을 반환합니다.")
//    @ApiResponse(responseCode = "200")
//    @PostMapping(path = "/search2")
//    public Slice<PharmacyIndex> getPharmacySearch2(
//            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "검색 요청정보") @RequestBody PharmacySearchRequestDto request,
//            @Parameter(in = ParameterIn.QUERY, hidden = true) @PageableDefault(size = 30, sort = "RELEVANT") Pageable pageable
//    ) {
//        return pharmacySearchService.searchPharmacy2(request, pageable);
//    }

//    @Tag(name = "PharmacySearch", description = "약국 조회")
//    @Operation(summary = "키워드 약국 검색", description = "검색 키워드와 필터 조건을 기반으로 약국 목록을 반환합니다.")
//    @ApiResponse(responseCode = "200")
//    @PostMapping(path = "/search/keyword")
//    public Slice<PharmacyItemDto> getPharmacyKeywordSearch(
//            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "검색 요청정보") @RequestBody PharmacyKeywordSearchRequestDto request,
//            @Parameter(in = ParameterIn.QUERY, hidden = true) @PageableDefault(size = 30, sort = "RELEVANT") Pageable pageable
//    ) {
//        return new SliceImpl<>(new ArrayList<>());
//    }

    @Tag(name = "PharmacySearch", description = "약국 조회")
    @Operation(summary = "약국 상세 정보 조회", description = "약국의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 약국이 존재하지 않습니다.", content = @Content(schema = @Schema(hidden = true))),
    })
    @GetMapping(path = "/{pharmacyId}")
    public PharmacyDetailDto getPharmacySearch(
            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
            @Parameter(in = ParameterIn.PATH, description = "약국 고유 id") @PathVariable String pharmacyId
    ) {
        return pharmacyService.getPharmacyDetail(user, pharmacyId);
    }

//    @Tag(name = "PharmacySearch", description = "약국 조회")
//    @Operation(summary = "약국 상세 정보 조회", description = "약국의 상세 정보를 조회합니다.")
//    @GetMapping(path = "/{pharmacyId}")
//    public PharmacyDetailDto getPharmacy(
//            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
//            @Parameter(in = ParameterIn.PATH, description = "약국 고유 id") @PathVariable String pharmacyId
//    ) {
//        return new PharmacyDetailDto();
//    }

    @Tag(name = "Favorite", description = "약국 찜목록")
    @Operation(summary = "찜목록 약국 추가", description = "약국을 찜목록에 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 약국이 존재하지 않습니다.", content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping(path = "/{pharmacyId}/favorite")
    public PharmacyDetailDto postPharmacyFavorite(
            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
            @Parameter(in = ParameterIn.PATH, description = "약국 고유 id") @PathVariable String pharmacyId) {
        return pharmacyFavoriteService.favoritePharmacy(user, pharmacyId);
    }

    @Tag(name = "Favorite", description = "약국요 찜목록")
    @Operation(summary = "찜목록에서 약국 삭제 (단건)", description = "약국을 찜목록에서 제거합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 약국이 존재하지 않습니다.", content = @Content(schema = @Schema(hidden = true))),
    })
    @DeleteMapping(path = "/{pharmacyId}/favorite")
    public void deletePharmacyFavorite(
            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
            @Parameter(in = ParameterIn.PATH, description = "약국 고유 id") @PathVariable String pharmacyId) {
        pharmacyFavoriteService.unfavoritePharmacy(user, pharmacyId);
    }

    @Tag(name = "Favorite", description = "약국 찜목록")
    @Operation(summary = "약국 찜목록 조회", description = "우선순위에 의해 정렬된 약국 찜목록을 반환합니다.")
    @ApiResponse(responseCode = "200")
    @GetMapping(path = "/favorite")
    public Slice<PharmacyFavoriteItemDto> getPharmacyFavorite(
            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
            @Parameter(in = ParameterIn.QUERY, description = "현재 디바이스 GPS좌표(위도)") @Nullable @RequestParam(required = false) Double latitude,
            @Parameter(in = ParameterIn.QUERY, description = "현재 디바이스 GPS좌표(경도)") @Nullable @RequestParam(required = false) Double longitude,
            @Parameter(in = ParameterIn.QUERY, hidden = true) @PageableDefault(size = 30, sort = "RELEVANT") Pageable pageable) {
        return pharmacyFavoriteService.favoritePharmacies(user, pageable);
    }

    @Tag(name = "Favorite", description = "약국 찜목록")
    @Operation(summary = "찜목록에서 약국 묶음 삭제 (다건)", description = "찜목록에서 약국 묶음을 제거합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 약국이 존재하지 않습니다.", content = @Content(schema = @Schema(hidden = true))),
    })
    @PostMapping(path = "/favorite/remove")
    public void removePharmacyFavoriteBulk(
            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "삭제할 약국 id 목록")
            @ArraySchema(schema = @Schema(ref = PharmacySchemas.ID))
            @RequestBody List<String> idsToRemove) {
        pharmacyFavoriteService.unfavoritePharmacies(user, idsToRemove);
    }

    @Tag(name = "Favorite", description = "약국 찜목록")
    @Operation(summary = "찜목록 약국 우선순위 목록 수정", description = "찜목록 약국 순서를 조정합니다. 순서와 약국 id와 쌍으로, 원하는 위치에 삽입합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "해당 ID의 약국이 존재하지 않습니다.", content = @Content(schema = @Schema(hidden = true))),
    })
    @PatchMapping(path = "/favorite/reorder")
    public void updatePharmacyFavorite(
            @Parameter(in = ParameterIn.HEADER, description = "PHR 계정", hidden = true) @NotNull @CurrentUser User user,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "수정할 목록") @RequestBody PharmacyFavoriteReorderRequestDto pharmacyFavoriteOrderDto) {
        pharmacyFavoriteService.reorderFavorite(user, pharmacyFavoriteOrderDto);
    }
}