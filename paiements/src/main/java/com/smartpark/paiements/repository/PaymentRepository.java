package com.smartpark.paiements.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<com.smartpark.paiements.domaine.entity.Payment, Long> {
    List<com.smartpark.paiements.domaine.entity.Payment> findByReservationId(String reservationId);
}
