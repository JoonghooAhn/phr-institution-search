package com.cenacle.edge.mapper;

import com.cenacle.edge.model.OpeningHours;
import com.cenacle.edge.model.OpeningHoursDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OpeningHoursMapper {
    OpeningHoursDto toDto(OpeningHours openingHours);
}
