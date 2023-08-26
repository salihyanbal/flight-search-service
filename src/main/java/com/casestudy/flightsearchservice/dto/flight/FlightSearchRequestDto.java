package com.casestudy.flightsearchservice.dto.flight;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightSearchRequestDto {

    @NotNull
    private String departureAirportCode;

    @NotNull
    private String destinationAirportCode;

    @NotNull
    private LocalDate departOn;

    private LocalDate returnOn;

}
