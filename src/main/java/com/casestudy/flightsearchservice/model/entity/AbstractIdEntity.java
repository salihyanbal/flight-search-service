package com.casestudy.flightsearchservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@MappedSuperclass
public abstract class AbstractIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid default gen_random_uuid()")
    private UUID id;
}
