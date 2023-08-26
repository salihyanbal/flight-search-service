package com.casestudy.flightsearchservice.service.impl;

import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.dto.flight.FlightRequestDto;
import com.casestudy.flightsearchservice.dto.flight.FlightSearchRequestDto;
import com.casestudy.flightsearchservice.exception.ServiceException;
import com.casestudy.flightsearchservice.model.entity.Airport;
import com.casestudy.flightsearchservice.model.entity.Flight;
import com.casestudy.flightsearchservice.model.enums.ErrorCodeEnum;
import com.casestudy.flightsearchservice.model.service.FlightSearchResponse;
import com.casestudy.flightsearchservice.repository.FlightRepository;
import com.casestudy.flightsearchservice.service.AirportService;
import com.casestudy.flightsearchservice.service.FlightService;
import com.casestudy.flightsearchservice.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    private final AirportService airportService;

    @Override
    public Flight create(FlightRequestDto flightRequestDto) {
        checkIsDepartureAndDestinationSame(flightRequestDto.getDepartureAirportCode(), flightRequestDto.getArrivalAirportCode());

        Airport departureAirport = airportService.findByAirportCode(flightRequestDto.getDepartureAirportCode());
        Airport destinationAirport = airportService.findByAirportCode(flightRequestDto.getArrivalAirportCode());

        Flight flightToSave = Flight.builder()
                .departure(departureAirport)
                .destination(destinationAirport)
                .departureDateTime(flightRequestDto.getDepartureDateTime())
                .arrivalDateTime(flightRequestDto.getArrivalDateTime())
                .price(flightRequestDto.getPrice())
                .build();

        return flightRepository.save(flightToSave);
    }

    @Override
    public void createAll(List<FlightRequestDto> flightRequestDtoList) {
        flightRequestDtoList.forEach(this::create);
    }

    @Override
    public Flight update(UUID id, FlightRequestDto flightRequestDto) {
        Flight foundFlight = findById(id);

        checkIsDepartureAndDestinationSame(flightRequestDto.getDepartureAirportCode(), flightRequestDto.getArrivalAirportCode());

        Airport departureAirport = airportService.findByAirportCode(flightRequestDto.getDepartureAirportCode());
        Airport destinationAirport = airportService.findByAirportCode(flightRequestDto.getArrivalAirportCode());

        foundFlight.setDeparture(departureAirport);
        foundFlight.setDestination(destinationAirport);
        foundFlight.setDepartureDateTime(flightRequestDto.getDepartureDateTime());
        foundFlight.setArrivalDateTime(flightRequestDto.getArrivalDateTime());
        foundFlight.setPrice(flightRequestDto.getPrice());

        return flightRepository.save(foundFlight);
    }

    @Override
    public void delete(UUID id) {
        Flight foundFlight = findById(id);
        flightRepository.delete(foundFlight);
    }

    @Override
    public Flight findById(UUID id) {
        return flightRepository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorCodeEnum.NOT_FOUND, "Flight not found by id!")
        );
    }

    @Override
    public FlightSearchResponse search(FlightSearchRequestDto flightSearchRequestDto) {
        checkIsReturnDateBeforeThanDepartDate(flightSearchRequestDto.getDepartOn(), flightSearchRequestDto.getReturnOn());
        Airport departureAirport = airportService.findByAirportCode(flightSearchRequestDto.getDepartureAirportCode());
        Airport destinationAirport = airportService.findByAirportCode(flightSearchRequestDto.getDestinationAirportCode());

        LocalDateTime departStart = flightSearchRequestDto.getDepartOn().atStartOfDay();
        LocalDateTime departEnd = flightSearchRequestDto.getDepartOn().atTime(LocalTime.MAX);

        List<Flight> departureFlightList = flightRepository.search(
                departureAirport.getId(),
                destinationAirport.getId(),
                departStart,
                departEnd
        );
        List<Flight> returnFlightList = null;
        if(flightSearchRequestDto.getReturnOn() != null){
            LocalDateTime returnStart = flightSearchRequestDto.getReturnOn().atStartOfDay();
            LocalDateTime returnEnd = flightSearchRequestDto.getReturnOn().atTime(LocalTime.MAX);
            returnFlightList = flightRepository.search(
                    destinationAirport.getId(),
                    departureAirport.getId(),
                    returnStart,
                    returnEnd
            );
        }

        FlightSearchResponse searchResponse = FlightSearchResponse.builder()
                .departureFlights(departureFlightList)
                .returnFlights(returnFlightList)
                .build();
        return searchResponse;
    }

    @Override
    public Page<Flight> findAll(PageableRequest pageableRequest) {
        Pageable pageable = PageUtil.from(pageableRequest);
        return flightRepository.findAll(pageable);
    }

    private void checkIsDepartureAndDestinationSame(String departureAirportCode, String destinationAirportCode){
        if(departureAirportCode.equals(destinationAirportCode)){
            throw new ServiceException(ErrorCodeEnum.VALIDATION, "The departure airport code and the arrival airport code cannot be the same!");
        }
    }

    private void checkIsReturnDateBeforeThanDepartDate(LocalDate departDate, LocalDate returnDate){
        if(departDate.isAfter(returnDate)){
            throw new ServiceException(ErrorCodeEnum.VALIDATION, "The return date cannot be before than the depart date!");
        }
    }
}
