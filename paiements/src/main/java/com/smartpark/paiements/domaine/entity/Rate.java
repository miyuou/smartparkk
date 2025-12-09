package com.smartpark.paiements.domaine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rates")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Id de la zone venant du microservice places
    @Column(name = "zone_id", nullable = false)
    private Long zoneId;

    @Column(name = "hourly_rate", nullable = false)
    private Double hourlyRate;

    @Column(length = 10)
    private String currency; // "MAD" par exemple
}
