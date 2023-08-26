package com.casestudy.flightsearchservice.repository;

import com.casestudy.flightsearchservice.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    @Query("Select flight from Flight flight " +
            "where flight.departure.id = :departureId " +
            "and flight.destination.id = :destinationId " +
            "and flight.departureDateTime >= :minDate and flight.departureDateTime <= :maxDate")
    List<Flight> search(
            UUID departureId,
            UUID destinationId,
            LocalDateTime minDate,
            LocalDateTime maxDate
    );
}
