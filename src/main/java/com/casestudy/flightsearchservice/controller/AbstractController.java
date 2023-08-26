package com.casestudy.flightsearchservice.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@Getter
@RequiredArgsConstructor
public abstract class AbstractController {
    private final ModelMapper modelMapper;
}
