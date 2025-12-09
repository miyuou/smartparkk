package org.example.reservationservice.domain.services.impl;

import org.example.reservationservice.feign.PlacesClient;
import org.example.reservationservice.domain.entities.Reservation;
import org.example.reservationservice.domain.entities.User;
import org.example.reservationservice.domain.entities.Vehicule;
import org.example.reservationservice.domain.enums.ReservationStatus;
import org.example.reservationservice.domain.repositories.ReservationRepository;
import org.example.reservationservice.domain.repositories.UserRepository;
import org.example.reservationservice.domain.repositories.VehiculeRepository;
import org.example.reservationservice.domain.services.ReservationService;
import org.example.reservationservice.domain.dto.feign.ParkingSpotFeignResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final VehiculeRepository vehiculeRepository;
    private final PlacesClient placesClient;      // 🔹 nouveau : client Feign vers microservice places

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  UserRepository userRepository,
                                  VehiculeRepository vehiculeRepository,
                                  PlacesClient placesClient) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.placesClient = placesClient;
    }

    @Override
    public Reservation createReservation(Reservation reservation) {

        // 1. Vérifier unicité de reservationId
        if (reservationRepository.existsByReservationId(reservation.getReservationId())) {
            throw new RuntimeException("Reservation with ID " + reservation.getReservationId() + " already exists");
        }

        // 2. Vérifier que l'utilisateur existe
        User user = userRepository.findById(reservation.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + reservation.getUser().getId()));

        // 3. Vérifier que le véhicule existe
        Vehicule vehicle = vehiculeRepository.findById(reservation.getVehicle().getId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id: " + reservation.getVehicle().getId()));

        // 4. 🔹 Vérifier la place via le microservice PLACES avec OpenFeign
        Long spotIdAsLong = parseSpotId(reservation.getSpotId());
        ParkingSpotFeignResponse spot = placesClient.getSpotById(spotIdAsLong);

        if (spot == null) {
            throw new RuntimeException("Parking spot not found with id: " + reservation.getSpotId());
        }
        if (Boolean.FALSE.equals(spot.getActive())) {
            throw new RuntimeException("Parking spot " + reservation.getSpotId() + " is not active");
        }

        // (Éventuellement : vérifier disponibilité via un autre endpoint Feign /availability)

        // 5. Associer les objets validés
        reservation.setUser(user);
        reservation.setVehicle(vehicle);

        // 6. Sauvegarde
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));

        // 🔹 Si on change la place, on re-vérifie via Feign
        if (reservation.getSpotId() != null &&
                !reservation.getSpotId().equals(existingReservation.getSpotId())) {

            Long spotIdAsLong = parseSpotId(reservation.getSpotId());
            ParkingSpotFeignResponse spot = placesClient.getSpotById(spotIdAsLong);

            if (spot == null) {
                throw new RuntimeException("Parking spot not found with id: " + reservation.getSpotId());
            }
            if (Boolean.FALSE.equals(spot.getActive())) {
                throw new RuntimeException("Parking spot " + reservation.getSpotId() + " is not active");
            }

            existingReservation.setSpotId(reservation.getSpotId());
        }

        existingReservation.setStartTime(reservation.getStartTime());
        existingReservation.setEndTime(reservation.getEndTime());
        existingReservation.setStatus(reservation.getStatus());

        return reservationRepository.save(existingReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reservation not found with id: " + id);
        }
        reservationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationByReservationId(String reservationId) {
        return reservationRepository.findByReservationId(reservationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByUser(User user) {
        return reservationRepository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByVehicle(Vehicule vehicle) {
        return reservationRepository.findByVehicle(vehicle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByTimeRange(LocalDateTime start, LocalDateTime end) {
        return reservationRepository.findByStartTimeBetween(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByReservationId(String reservationId) {
        return reservationRepository.existsByReservationId(reservationId);
    }

    // 🔹 utilitaire : convertir le spotId (String) de l'entité en Long pour l'appel Feign
    private Long parseSpotId(String spotId) {
        try {
            return Long.parseLong(spotId);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid spotId format (expected numeric id) : " + spotId, e);
        }
    }
}
