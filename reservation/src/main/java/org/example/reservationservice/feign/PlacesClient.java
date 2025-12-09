package org.example.reservationservice.feign;

import org.example.reservationservice.domain.dto.feign.AvailabilityFeignResponse;
import org.example.reservationservice.domain.dto.feign.ParkingSpotFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "places-service",
        path = "/api/spots"
)
public interface PlacesClient {

    @GetMapping("/{id}")
    ParkingSpotFeignResponse getSpotById(@PathVariable("id") Long id);

    @GetMapping("/availability/{id}")   // à adapter si ton AvailabilityController est différent
    List<AvailabilityFeignResponse> getAvailabilityBySpot(@PathVariable("id") Long id);
}
