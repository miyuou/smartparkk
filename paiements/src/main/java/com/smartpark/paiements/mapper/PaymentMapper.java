package com.smartpark.paiements.mapper;

import com.smartpark.paiements.domaine.dto.PaymentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toDto(com.smartpark.paiements.domaine.entity.Payment entity);
    com.smartpark.paiements.domaine.entity.Payment toEntity(PaymentDto dto);
}
