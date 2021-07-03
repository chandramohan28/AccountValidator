package com.jpmc.AccountValidator.exception;

public class BusinessException extends Exception {
    private int statusCode;

    public BusinessException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;

    }

    public int getStatusCode() {
        return statusCode;
    }
}
