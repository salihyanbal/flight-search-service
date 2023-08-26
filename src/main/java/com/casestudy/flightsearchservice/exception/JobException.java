package com.casestudy.flightsearchservice.exception;

public class JobException extends RuntimeException{
    private final String message;

    public JobException(final String message) {
        this.message = message;
    }
}
