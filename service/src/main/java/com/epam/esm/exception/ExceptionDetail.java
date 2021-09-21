package com.epam.esm.exception;

public class ExceptionDetail {
    private final String errorMessage;
    private final String errorCode;
    private final String incorrectParameter;

    public ExceptionDetail(String errorMessage, String errorCode, String incorrectParameter) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.incorrectParameter = incorrectParameter;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getIncorrectParameter() {
        return incorrectParameter;
    }
}
