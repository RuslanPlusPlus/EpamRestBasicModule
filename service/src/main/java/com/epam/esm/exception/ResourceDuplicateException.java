package com.epam.esm.exception;

public class ResourceDuplicateException extends RuntimeException{
    private ExceptionDetail exceptionDetail;

    public ResourceDuplicateException(ExceptionDetail exceptionDetail){
        this.exceptionDetail = exceptionDetail;
    }

    public ExceptionDetail getExceptionDetail() {
        return exceptionDetail;
    }

    public void setExceptionDetail(ExceptionDetail exceptionDetail) {
        this.exceptionDetail = exceptionDetail;
    }
}
