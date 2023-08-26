package com.casestudy.flightsearchservice.dto.external;

import com.casestudy.flightsearchservice.dto.flight.FlightRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyFlightResponseDto {

    private List<FlightRequestDto> flights;

    private int total;
}
