package com.casestudy.flightsearchservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "flights")
public class Flight extends AbstractIdEntity{

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", columnDefinition = "uuid", nullable = false)
    private Airport departure;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", columnDefinition = "uuid", nullable = false)
    private Airport destination;

    @Column(name = "departure_date_time", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "arrival_date_time", nullable = false)
    private LocalDateTime arrivalDateTime;

    @Column(name = "price", nullable = false)
    private Double price;
}
