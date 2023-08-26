package com.casestudy.flightsearchservice.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "airports")
public class Airport extends AbstractIdEntity{

    @Column(name = "city", columnDefinition = "varchar(100)", nullable = false)
    private String city;

    @Column(name = "airport_code", columnDefinition = "varchar(5)", nullable = false, unique = true)
    private String airportCode;
}
