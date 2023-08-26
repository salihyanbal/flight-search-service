package com.casestudy.flightsearchservice.dto.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PageableResponse {

    private int pageNumber;

    private int pageSize;

    private long count;
}
