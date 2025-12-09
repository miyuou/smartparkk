package com.smartpark.paiements.domaine.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "transactions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "gateway_ref", length = 100)
    private String gatewayRef;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // "INITIATED","CONFIRMED","REFUSED"
}

