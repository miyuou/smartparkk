package com.smartpark.paiements.repository;

import com.smartpark.paiements.domaine.entity.Rate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByZoneId(Long zoneId);
}
