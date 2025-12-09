package com.smartpark.paiements.controller;

import com.smartpark.paiements.domaine.dto.PaymentDto;
import com.smartpark.paiements.service.PaymentService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping
    public List<PaymentDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PaymentDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/by-reservation/{reservationId}")
    public List<PaymentDto> getByReservation(@PathVariable String reservationId) {
        return service.getByReservation(reservationId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentDto create(@Valid @RequestBody PaymentDto dto) {
        return service.create(dto);
    }
}

