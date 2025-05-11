package br.com.kaikedev.orderservice.Entity.Dto;

import java.time.LocalDateTime;

public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;
    private String errorCode;

    public ErrorDetails(LocalDateTime now, String message, String description, String error) {
        this.timestamp = now;
        this.message = message;
        this.details = description;
        this.errorCode = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
