package com.epam.esm.exceptionConfig;

public class ErrorResponseMessage {
    private final String errorMessage;
    private final String errorCode;

    public ErrorResponseMessage(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
