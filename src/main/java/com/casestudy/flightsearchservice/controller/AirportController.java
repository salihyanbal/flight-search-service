package com.casestudy.flightsearchservice.controller;

import com.casestudy.flightsearchservice.dto.airport.AirportRequestDto;
import com.casestudy.flightsearchservice.dto.airport.AirportResponseDto;
import com.casestudy.flightsearchservice.dto.common.PageResponse;
import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.model.entity.Airport;
import com.casestudy.flightsearchservice.service.AirportService;
import com.casestudy.flightsearchservice.util.CollectionUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(path = "airport")
public class AirportController extends AbstractController{

    private final AirportService airportService;

    @Autowired
    public AirportController(ModelMapper modelMapper, AirportService airportService) {
        super(modelMapper);
        this.airportService = airportService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<AirportResponseDto> create(@Valid @RequestBody AirportRequestDto airportRequest){
        Airport savedAirport = airportService.create(airportRequest);
        return ResponseEntity.ok(
                getModelMapper().map(savedAirport, AirportResponseDto.class)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AirportResponseDto> update(@PathVariable UUID id, @Valid @RequestBody AirportRequestDto airportRequest){
        Airport updatedAirport = airportService.update(id, airportRequest);
        return ResponseEntity.ok(
                getModelMapper().map(updatedAirport, AirportResponseDto.class)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        airportService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<AirportResponseDto>> findAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        PageableRequest pageableRequest = PageableRequest.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
        Page<Airport> allAirportPage = airportService.findAll(pageableRequest);
        List<AirportResponseDto> airportResponseList = CollectionUtil.map(
                allAirportPage.getContent(),
                (airport) -> getModelMapper().map(airport, AirportResponseDto.class)
        );
        PageResponse<AirportResponseDto> pageResponse = PageResponse.<AirportResponseDto>pageResponseBuilder()
                .data(airportResponseList)
                .pageSize(allAirportPage.getSize())
                .pageNumber(allAirportPage.getNumber())
                .count(allAirportPage.getTotalElements())
                .build();
        return ResponseEntity.ok(
                pageResponse
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponseDto> findById(@PathVariable UUID id){
        Airport foundAirport = airportService.findById(id);
        return ResponseEntity.ok(
                getModelMapper().map(foundAirport, AirportResponseDto.class)
        );
    }

    @GetMapping("/code/{airportCode}")
    public ResponseEntity<AirportResponseDto> findByAirportCode(@PathVariable String airportCode){
        Airport foundAirport = airportService.findByAirportCode(airportCode);
        return ResponseEntity.ok(
                getModelMapper().map(foundAirport, AirportResponseDto.class)
        );
    }
}
