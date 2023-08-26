package com.casestudy.flightsearchservice.dto.common;

import lombok.*;

@Getter
@Setter
@Builder
public class PageableRequest {

    private int pageNumber;

    private int pageSize;
}
