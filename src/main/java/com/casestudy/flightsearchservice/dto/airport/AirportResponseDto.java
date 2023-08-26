package com.casestudy.flightsearchservice.dto.airport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportResponseDto {

    private UUID id;

    private String city;

    private String airportCode;
}