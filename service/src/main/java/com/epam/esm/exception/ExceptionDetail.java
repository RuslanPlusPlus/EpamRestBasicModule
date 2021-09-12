package com.epam.esm.exception;

public class ExceptionDetail {
    private String errorMessage;
    private String ErrorCode;
    private String incorrectParameter;

    public ExceptionDetail(String errorMessage, String errorCode, String incorrectParameter) {
        this.errorMessage = errorMessage;
        ErrorCode = errorCode;
        this.incorrectParameter = incorrectParameter;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public String getIncorrectParameter() {
        return incorrectParameter;
    }
}
