package com.smartpark.paiements.service;

import com.smartpark.paiements.domaine.dto.PaymentDto;
import java.util.List;

public interface PaymentService {
    PaymentDto create(PaymentDto dto);
    PaymentDto get(Long id);
    List<PaymentDto> getByReservation(String reservationId);
    List<PaymentDto> getAll();
}
