package org.example.reservationservice.domain.dto.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailabilityFeignResponse {
    private Long id;
    private Long spotId;
    private Boolean available;
    private String timestamp;
}

