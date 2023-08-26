package com.casestudy.flightsearchservice.service.impl;

import com.casestudy.flightsearchservice.dto.airport.AirportRequestDto;
import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.exception.ServiceException;
import com.casestudy.flightsearchservice.model.entity.Airport;
import com.casestudy.flightsearchservice.model.enums.ErrorCodeEnum;
import com.casestudy.flightsearchservice.repository.AirportRepository;
import com.casestudy.flightsearchservice.service.AirportService;
import com.casestudy.flightsearchservice.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;

    @Override
    public Airport create(AirportRequestDto airportRequestDto) {
        boolean doesExist = doesExistsByAirportCode(airportRequestDto.getAirportCode());
        if(doesExist){
            throw new ServiceException(ErrorCodeEnum.ALREADY_EXIST, "Airport already exist with this code!");
        }

        Airport airportToSave = Airport.builder()
                .city(airportRequestDto.getCity())
                .airportCode(airportRequestDto.getAirportCode())
                .build();
        return airportRepository.save(airportToSave);
    }

    @Override
    public Airport update(UUID id, AirportRequestDto airportRequestDto) {
        boolean doesExist = doesExistsByAirportCode(airportRequestDto.getAirportCode());
        if(doesExist){
            throw new ServiceException(ErrorCodeEnum.ALREADY_EXIST, "Airport already exist with this code!");
        }

        Airport airportToUpdate = findById(id);
        airportToUpdate.setCity(airportRequestDto.getCity());
        airportToUpdate.setAirportCode(airportRequestDto.getAirportCode());
        return airportRepository.save(airportToUpdate);
    }

    @Override
    public void delete(UUID id) {
        Airport foundAirport = findById(id);
        airportRepository.delete(foundAirport);
    }

    @Override
    public Airport findById(UUID id) {
        return airportRepository.findById(id).orElseThrow(
                () -> new ServiceException(ErrorCodeEnum.NOT_FOUND, "Airport not found by id!")
        );
    }

    @Override
    public Airport findByAirportCode(String airportCode) {
        return airportRepository.findByAirportCode(airportCode).orElseThrow(
                () -> new ServiceException(ErrorCodeEnum.NOT_FOUND, "Airport not found by code!")
        );
    }

    @Override
    public Page<Airport> findAll(PageableRequest pageableRequest) {
        Pageable pageable = PageUtil.from(pageableRequest);
        return airportRepository.findAll(pageable);
    }

    private boolean doesExistsByAirportCode(String airportCode){
        return airportRepository.existsByAirportCode(airportCode);
    }
}
