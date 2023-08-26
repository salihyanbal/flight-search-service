package com.casestudy.flightsearchservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCodeEnum {

    NOT_IMPLEMENTED("FS000", HttpStatus.NO_CONTENT),
    GENERAL("FS001", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND("FS002", HttpStatus.NOT_FOUND),
    ALREADY_EXIST("FS003", HttpStatus.FOUND),
    VALIDATION("FS004", HttpStatus.BAD_REQUEST),
    SECURITY_MISSING_CREDENTIALS("FS005", HttpStatus.BAD_REQUEST),
    SECURITY_BAD_CREDENTIALS("FS006", HttpStatus.BAD_REQUEST),
    SECURITY_USER_AUTHORIZATION_FAIL("FS007", HttpStatus.UNAUTHORIZED),
    SECURITY_FORBIDDEN("FS008", HttpStatus.FORBIDDEN),
    INVALID_CLIENT_REQUEST("FS009", HttpStatus.BAD_REQUEST);

    private final String code;

    private final HttpStatus httpStatus;
}
