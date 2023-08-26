package com.casestudy.flightsearchservice.dto.common;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder(builderMethodName = "pageResponseBuilder")
public class PageResponse<T> extends PageableResponse {

    private List<T> data;
}
