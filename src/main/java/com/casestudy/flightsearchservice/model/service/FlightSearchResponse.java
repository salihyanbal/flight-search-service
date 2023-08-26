package com.casestudy.flightsearchservice.model.service;

import com.casestudy.flightsearchservice.model.entity.Flight;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchResponse {

    private List<Flight> departureFlights;

    private List<Flight> returnFlights;
}