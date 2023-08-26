package com.casestudy.flightsearchservice.service;

import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.dto.flight.FlightRequestDto;
import com.casestudy.flightsearchservice.dto.flight.FlightSearchRequestDto;
import com.casestudy.flightsearchservice.model.entity.Flight;
import com.casestudy.flightsearchservice.model.service.FlightSearchResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface FlightService {

    Flight create(FlightRequestDto flightRequestDto);

    void createAll(List<FlightRequestDto> flightRequestDtoList);

    Flight update(UUID id, FlightRequestDto flightRequestDto);

    void delete(UUID id);

    Flight findById(UUID id);

    FlightSearchResponse search(FlightSearchRequestDto flightSearchRequestDto);

    Page<Flight> findAll(PageableRequest pageableRequest);
}
