package com.smartpark.paiements.domaine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "receipts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
