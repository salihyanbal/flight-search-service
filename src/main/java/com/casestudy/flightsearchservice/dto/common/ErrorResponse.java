package com.casestudy.flightsearchservice.dto.common;

import com.casestudy.flightsearchservice.model.enums.ErrorCodeEnum;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    public static final ErrorResponseBuilder GENERAL = ErrorResponse.builder()
            .code(ErrorCodeEnum.GENERAL.getCode())
            .timestamp(Timestamp.valueOf(LocalDateTime.now()))
            .message("An error occured!");

    private Timestamp timestamp;

    private String code;

    private String message;
}
