package com.casestudy.flightsearchservice.job;

import com.casestudy.flightsearchservice.dto.external.DailyFlightResponseDto;
import com.casestudy.flightsearchservice.exception.JobException;
import com.casestudy.flightsearchservice.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Component
public class CreateDailyFlightsJob {

    @Value("${external.url.daily-flights}")
    private String dailyFlightUrl;

    private final FlightService flightService;

    //cron second, minute, hour, day of month, month, day(s) of week
    // * -> match any
    // */X -> every X
    // ? -> no specific value
    @Scheduled(cron = "0 0 1 * * ?", zone="Europe/Istanbul")
    public void createDailyFlights(){
        DailyFlightResponseDto dailyFlights = fetchDailyFlights();
        flightService.createAll(dailyFlights.getFlights());
        log.info("%d daily flights created at %s".formatted(dailyFlights.getTotal(), LocalDateTime.now()));
    }

    private DailyFlightResponseDto fetchDailyFlights(){
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<DailyFlightResponseDto> response = restTemplate.getForEntity(dailyFlightUrl, DailyFlightResponseDto.class);
            return response.getBody();
        } catch (RestClientException exception){
            String errorMessage = "Daily flights fetch failed: %s %s".formatted(exception.getMessage(), exception);
            log.error(errorMessage);
            throw new JobException(errorMessage);
        }

    }
}
