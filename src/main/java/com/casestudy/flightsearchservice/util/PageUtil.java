package com.casestudy.flightsearchservice.util;

import com.casestudy.flightsearchservice.dto.common.PageableRequest;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@UtilityClass
public class PageUtil {

    public Pageable from(PageableRequest pageableRequest){
        return PageRequest.of(pageableRequest.getPageNumber(), pageableRequest.getPageSize());
    }
}
