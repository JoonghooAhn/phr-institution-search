package com.cenacle.edge.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "유저정보(UserDto)")
public class UserDto {
    @Schema(title = "유저id")
    private String id;

    @Schema(title = "유저명")
    private String name;

    @Schema(title = "유저 이미지 fileId")
    private String imageFileId;
}
