package com.casestudy.flightsearchservice.advice;

import com.casestudy.flightsearchservice.dto.common.ErrorResponse;
import com.casestudy.flightsearchservice.exception.CustomAuthenticationException;
import com.casestudy.flightsearchservice.exception.ServiceException;
import com.casestudy.flightsearchservice.model.enums.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.warn("handling general exception: " + ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.GENERAL.build());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        log.warn("handling service exception: " + ex.getMessage(), ex);
        ErrorResponse errorResponse = createErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class,
            NoHandlerFoundException.class})
    public ResponseEntity<ErrorResponse> handleInvalidClientRequest(final Exception ex) {
        ErrorResponse errorResponse = createErrorResponse(ErrorCodeEnum.INVALID_CLIENT_REQUEST.getCode(), null);
        return ResponseEntity.status(ErrorCodeEnum.INVALID_CLIENT_REQUEST.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(CustomAuthenticationException ex) {
        log.warn("handling authentication exception: " + ex.getMessage(), ex);
        ErrorResponse errorResponse = createErrorResponse(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(AccessDeniedException ex) {
        log.warn("handling authorization exception: " + ex.getMessage(), ex);
        ErrorResponse errorResponse = createErrorResponse(ErrorCodeEnum.SECURITY_FORBIDDEN.getCode(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    private ErrorResponse createErrorResponse(final String errorCode, final String message) {
        return ErrorResponse.builder()
                .code(errorCode)
                .message(message)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }

}
