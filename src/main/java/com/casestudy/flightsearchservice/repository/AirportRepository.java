package com.casestudy.flightsearchservice.repository;

import com.casestudy.flightsearchservice.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AirportRepository extends JpaRepository<Airport, UUID> {
    Optional<Airport> findByAirportCode(String airportCode);
    boolean existsByAirportCode(String airportCode);
}
