package com.casestudy.flightsearchservice.controller;

import com.casestudy.flightsearchservice.dto.common.PageResponse;
import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import com.casestudy.flightsearchservice.dto.flight.FlightRequestDto;
import com.casestudy.flightsearchservice.dto.flight.FlightResponseDto;
import com.casestudy.flightsearchservice.dto.flight.FlightSearchRequestDto;
import com.casestudy.flightsearchservice.dto.flight.FlightSearchResponseDto;
import com.casestudy.flightsearchservice.model.entity.Flight;
import com.casestudy.flightsearchservice.model.service.FlightSearchResponse;
import com.casestudy.flightsearchservice.service.FlightService;
import com.casestudy.flightsearchservice.util.CollectionUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "flight")
public class FlightController extends AbstractController{

    private final FlightService flightService;

    public FlightController(ModelMapper modelMapper, FlightService flightService) {
        super(modelMapper);
        this.flightService = flightService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<FlightResponseDto> create(@Valid @RequestBody FlightRequestDto flightRequestDto){
        Flight savedFlight = flightService.create(flightRequestDto);
        return ResponseEntity.ok(
                getModelMapper().map(savedFlight, FlightResponseDto.class)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FlightResponseDto> update(@PathVariable UUID id, @Valid @RequestBody FlightRequestDto flightRequestDto){
        Flight updatedFlight = flightService.update(id, flightRequestDto);
        return ResponseEntity.ok(
                getModelMapper().map(updatedFlight, FlightResponseDto.class)
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        flightService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<FlightResponseDto>> findAll(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ){
        PageableRequest pageableRequest = PageableRequest.builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
        Page<Flight> allFlightPage = flightService.findAll(pageableRequest);
        List<FlightResponseDto> flightResponseList = CollectionUtil.map(
                allFlightPage.getContent(),
                (flight) -> getModelMapper().map(flight, FlightResponseDto.class)
        );
        PageResponse<FlightResponseDto> pageResponse = PageResponse.<FlightResponseDto>pageResponseBuilder()
                .data(flightResponseList)
                .pageSize(allFlightPage.getSize())
                .pageNumber(allFlightPage.getNumber())
                .count(allFlightPage.getTotalElements())
                .build();
        return ResponseEntity.ok(
                pageResponse
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDto> findById(@PathVariable UUID id){
        Flight foundFlight = flightService.findById(id);
        return ResponseEntity.ok(
                getModelMapper().map(foundFlight, FlightResponseDto.class)
        );
    }

    @PostMapping("/search")
    public ResponseEntity<FlightSearchResponseDto> search(@RequestBody FlightSearchRequestDto flightSearchRequestDto){
        FlightSearchResponse searchResponse = flightService.search(flightSearchRequestDto);
        return ResponseEntity.ok(
                getModelMapper().map(searchResponse, FlightSearchResponseDto.class)
        );
    }
}
