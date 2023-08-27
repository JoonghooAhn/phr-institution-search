package com.cenacle.edge.mapper;

import com.cenacle.edge.model.index.ClinicIndex;
import com.cenacle.edge.model.ClinicSimpleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClinicMapper {
    ClinicSimpleDto toSimpleDto(ClinicIndex clinicIndex);
}
