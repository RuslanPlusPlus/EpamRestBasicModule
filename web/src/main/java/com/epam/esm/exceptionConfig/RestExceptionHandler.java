package com.epam.esm.exceptionConfig;

import com.epam.esm.exception.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(IncorrectParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> handleIncorrectParamException(IncorrectParamException exception){
        List<ErrorResponse> errorResponseList = new ArrayList<>();
        exception.getExceptionDetailList().forEach(
                exceptionDetail -> {
                    String errorMessage = messageSource.getMessage(
                            exceptionDetail.getErrorMessage(),
                            new String[]{exceptionDetail.getIncorrectParameter()},
                            LocaleContextHolder.getLocale());
                    String errorCode = exceptionDetail.getErrorCode();
                    errorResponseList.add(new ErrorResponse(errorMessage, errorCode));
                }
        );
        return errorResponseList;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception){
        ExceptionDetail exceptionDetail = exception.getExceptionDetail();
        String errorMessage = messageSource.getMessage(
                exceptionDetail.getErrorMessage(),
                new String[]{exceptionDetail.getIncorrectParameter()},
                LocaleContextHolder.getLocale());
        String errorCode = exceptionDetail.getErrorCode();
        return new ErrorResponse(errorMessage, errorCode);
    }

    @ExceptionHandler(OperationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOperationException(OperationException exception){
        String errorMessage = messageSource.getMessage(
                exception.getMessage(),
                new String[]{},
                LocaleContextHolder.getLocale());
        String errorCode = exception.getErrorCode();
        return new ErrorResponse(errorMessage, errorCode);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(messageSource.getMessage(
                ResponseMessage.MEDIA_NOT_SUPPORTED,
                new String[]{},
                LocaleContextHolder.getLocale())
        );
        errorResponse.setErrorCode(String.valueOf(status.value()));
        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(messageSource.getMessage(
                ResponseMessage.METHOD_NOT_ALLOWED,
                new String[]{},
                LocaleContextHolder.getLocale())
        );
        errorResponse.setErrorCode(String.valueOf(status.value()));
        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(messageSource.getMessage(
                ResponseMessage.MESSAGE_NOT_READABLE,
                new String[]{},
                LocaleContextHolder.getLocale())
        );
        errorResponse.setErrorCode(String.valueOf(status.value()));
        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(messageSource.getMessage(
                ResponseMessage.INVALID_PARAM_FORMAT,
                new String[]{},
                LocaleContextHolder.getLocale())
        );
        errorResponse.setErrorCode(String.valueOf(status.value()));
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception){
        String errorMessage = messageSource.getMessage(
                ResponseMessage.INTERNAL_SERVER_ERROR,
                new String[]{},
                LocaleContextHolder.getLocale());
        errorMessage += ": " + exception.getMessage();
        return new ErrorResponse(errorMessage, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
