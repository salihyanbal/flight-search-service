package com.casestudy.flightsearchservice.exception;

import com.casestudy.flightsearchservice.model.enums.ErrorCodeEnum;
import lombok.Getter;

@Getter
public class CustomAuthenticationException extends org.springframework.security.core.AuthenticationException {

    private final String code;

    private final String message;

    public CustomAuthenticationException(final ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.name());
        this.code = errorCodeEnum.getCode();
        this.message = null;
    }
    public CustomAuthenticationException(final ErrorCodeEnum errorCodeEnum, final String message) {
        super(errorCodeEnum.name());
        this.code = errorCodeEnum.getCode();
        this.message = message;
    }

}
