package com.jpmc.AccountValidator.model;

import java.util.Date;

public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String requestURL;

    public ErrorMessage(int statusCode, Date timestamp, String message, String requestURL) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.requestURL = requestURL;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getRequestURL() {
        return requestURL;
    }
}