package com.epam.esm.exception;

public class AppException extends RuntimeException{

    private ExceptionDetail exceptionDetail;

    public AppException(ExceptionDetail exceptionDetail){
        this.exceptionDetail = exceptionDetail;
    }

    public ExceptionDetail getExceptionDetail() {
        return exceptionDetail;
    }
}
