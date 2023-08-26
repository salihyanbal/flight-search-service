package com.casestudy.flightsearchservice.dto.airport;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AirportRequestDto {

    @Size(max = 100)
    @NotNull
    private String city;

    @Size(max = 5)
    @NotNull
    private String airportCode;
}
