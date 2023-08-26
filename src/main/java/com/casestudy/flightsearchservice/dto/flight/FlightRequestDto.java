package com.casestudy.flightsearchservice.dto.flight;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequestDto {

    @NotNull
    private String departureAirportCode;

    @NotNull
    private String arrivalAirportCode;

    @NotNull
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureDateTime;

    @NotNull
    @DateTimeFormat(style = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalDateTime;

    @NotNull
    private Double price;
}
