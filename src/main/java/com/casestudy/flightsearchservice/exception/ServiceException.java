package com.casestudy.flightsearchservice.exception;

import com.casestudy.flightsearchservice.model.enums.ErrorCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final String code;

    private final String message;

    public ServiceException(final ErrorCodeEnum errorCodeEnum) {
        this.httpStatus = errorCodeEnum.getHttpStatus();
        this.code = errorCodeEnum.getCode();
        this.message = null;
    }

    public ServiceException(final ErrorCodeEnum errorCodeEnum, String message) {
        this.httpStatus = errorCodeEnum.getHttpStatus();
        this.code = errorCodeEnum.getCode();
        this.message = message;
    }
}
