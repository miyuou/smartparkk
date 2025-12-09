package com.smartpark.paiements.domaine.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Id de la réservation, vient du microservice reservation
    @Column(name = "reservation_id", nullable = false, length = 50)
    private String reservationId;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "method", length = 20, nullable = false)
    private String method; // "CARD", "WALLET", etc.

    @Column(name = "status", length = 20, nullable = false)
    private String status; // "PENDING","PAID","FAILED"

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
