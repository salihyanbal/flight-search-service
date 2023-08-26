package com.casestudy.flightsearchservice.service;

import com.casestudy.flightsearchservice.dto.airport.AirportRequestDto;
import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.model.entity.Airport;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AirportService {

    Airport create(AirportRequestDto airportRequestDto);

    Airport update(UUID id, AirportRequestDto airportRequestDto);

    void delete(UUID id);

    Airport findById(UUID id);

    Airport findByAirportCode(String airportCode);

    Page<Airport> findAll(PageableRequest pageableRequest);
}
