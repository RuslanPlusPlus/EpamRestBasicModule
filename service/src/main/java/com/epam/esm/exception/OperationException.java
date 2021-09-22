package com.epam.esm.exception;

public class OperationException extends RuntimeException{
    private String errorCode;

    public OperationException(String errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public OperationException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
