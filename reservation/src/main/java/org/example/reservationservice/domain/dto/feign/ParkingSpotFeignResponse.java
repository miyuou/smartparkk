package org.example.reservationservice.domain.dto.feign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingSpotFeignResponse {
    private Long id;
    private String code;
    private String type;
    private Boolean active;
    private Long zoneId;
}

