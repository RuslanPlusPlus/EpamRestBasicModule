package com.epam.esm.exception;

import java.util.List;

public class IncorrectParamException extends RuntimeException{

    private final List<ExceptionDetail> exceptionDetailList;

    public IncorrectParamException(List<ExceptionDetail> exceptionDetailList){
        this.exceptionDetailList = exceptionDetailList;
    }

    public List<ExceptionDetail> getExceptionDetailList() {
        return exceptionDetailList;
    }
}
