package com.ecommerce.project.Exceptions;

public class InvalidRequestException extends RuntimeException {

    private String message;

    public InvalidRequestException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}