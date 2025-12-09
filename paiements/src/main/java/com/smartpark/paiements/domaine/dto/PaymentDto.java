package com.smartpark.paiements.domaine.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private Long id;
    private String reservationId;
    private Double amount;
    private String method;
    private String status;
}
