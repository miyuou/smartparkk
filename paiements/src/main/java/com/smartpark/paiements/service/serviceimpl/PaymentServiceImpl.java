package com.smartpark.paiements.service.serviceimpl;

import com.smartpark.paiements.domaine.dto.PaymentDto;
import com.smartpark.paiements.mapper.PaymentMapper;
import com.smartpark.paiements.repository.PaymentRepository;
import com.smartpark.paiements.service.PaymentService;
import jakarta.ws.rs.NotFoundException;
import lombok.*;
import org.springframework.stereotype.Service;
import com.smartpark.paiements.domaine.entity.Payment;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repo;
    private final PaymentMapper mapper;

    @Override
    public PaymentDto create(PaymentDto dto) {
        Payment entity = mapper.toEntity(dto);
        if (entity.getStatus() == null) entity.setStatus("PENDING");
        Payment saved = repo.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public PaymentDto get(Long id) {
        Payment p = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id " + id));
        return mapper.toDto(p);
    }

    @Override
    public List<PaymentDto> getByReservation(String reservationId) {
        return repo.findByReservationId(reservationId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<PaymentDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }
}

