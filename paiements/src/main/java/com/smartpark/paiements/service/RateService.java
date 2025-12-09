package com.smartpark.paiements.service;

import com.smartpark.paiements.domaine.dto.RateDto;
import java.util.List;

public interface RateService {
    RateDto create(RateDto dto);
    RateDto update(Long id, RateDto dto);
    void delete(Long id);
    RateDto get(Long id);
    RateDto getByZone(Long zoneId);
    List<RateDto> getAll();
}
