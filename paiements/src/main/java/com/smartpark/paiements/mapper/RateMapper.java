package com.smartpark.paiements.mapper;

import com.smartpark.paiements.domaine.dto.RateDto;
import com.smartpark.paiements.domaine.entity.Rate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RateMapper {
    RateDto toDto(Rate entity);
    Rate toEntity(RateDto dto);
}
