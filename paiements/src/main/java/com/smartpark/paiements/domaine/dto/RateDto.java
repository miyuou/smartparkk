package com.smartpark.paiements.domaine.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateDto {
    private Long id;
    private Long zoneId;
    private Double hourlyRate;
    private String currency;
}
