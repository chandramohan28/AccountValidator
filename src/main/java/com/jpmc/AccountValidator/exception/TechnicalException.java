package com.jpmc.AccountValidator.exception;

public class TechnicalException extends Exception {
    private int statusCode;

    public TechnicalException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;

    }

    public int getStatusCode() {
        return statusCode;
    }
}
