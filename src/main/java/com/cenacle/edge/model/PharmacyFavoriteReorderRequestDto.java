package com.cenacle.edge.model;

import com.cenacle.edge.swagger.PharmacySchemas;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "약국 찜목록 우선순위 Dto(PharmacyFavoriteReorderRequestDto)", description = "찜목록 약국 순서 수정 Dto")
public class PharmacyFavoriteReorderRequestDto {
    @Schema(title = "찜목록 나열 순서", description = "찜목록에 표시될 나열 순서. 0부터 시작합니다.", example = "3")
    private Integer order;

    @Schema(ref = PharmacySchemas.ID)
    private String pharmacyId;
}