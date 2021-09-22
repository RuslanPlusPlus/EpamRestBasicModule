package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException{
    private ExceptionDetail exceptionDetail;

    public ResourceNotFoundException(ExceptionDetail exceptionDetail){
        this.exceptionDetail = exceptionDetail;
    }

    public ExceptionDetail getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(ExceptionDetail exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }
}

