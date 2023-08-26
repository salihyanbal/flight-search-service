package com.casestudy.flightsearchservice.dto.flight;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightSearchResponseDto {

    private List<FlightResponseDto> departureFlights;

    private List<FlightResponseDto> returnFlights;
}
