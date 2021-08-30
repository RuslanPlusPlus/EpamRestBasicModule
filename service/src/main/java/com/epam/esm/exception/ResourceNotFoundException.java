package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException{
    private String errorCode;

    public ResourceNotFoundException(String errorMessage, String errorCode){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

