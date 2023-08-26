package com.casestudy.flightsearchservice.dto.flight;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightResponseDto {

    private UUID id;

    private String departureAirportCode;

    private String destinationAirportCode;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    private Double price;
}
