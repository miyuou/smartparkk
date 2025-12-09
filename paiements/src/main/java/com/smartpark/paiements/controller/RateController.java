package com.smartpark.paiements.controller;

import com.smartpark.paiements.domaine.dto.RateDto;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.smartpark.paiements.service.RateService;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService service;

    @GetMapping
    public List<RateDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RateDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/by-zone/{zoneId}")
    public RateDto getByZone(@PathVariable Long zoneId) {
        return service.getByZone(zoneId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RateDto create(@Valid @RequestBody RateDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public RateDto update(@PathVariable Long id,
                          @Valid @RequestBody RateDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

