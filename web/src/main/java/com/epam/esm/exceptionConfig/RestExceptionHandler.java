package com.epam.esm.exceptionConfig;

import com.epam.esm.exception.AppException;
import com.epam.esm.exception.ExceptionDetail;
import com.epam.esm.exception.ResponseMessage;
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

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleResourceNotFoundException(AppException exception){
        ExceptionDetail exceptionDetail = exception.getExceptionDetail();
        String errorMessage = messageSource.getMessage(
                exceptionDetail.getErrorMessage(),
                new String[]{exceptionDetail.getIncorrectParameter()},
                LocaleContextHolder.getLocale());
        String errorCode = exceptionDetail.getErrorCode();
        return new ErrorResponse(errorMessage, errorCode);
    }

    /*@ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class,
            BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidParamFormatException(Exception exception) {
        String errorMessage = messag

        eSource.getMessage(
                ResponseMessage.INVALID_PARAM_FORMAT,null, LocaleContextHolder.getLocale());
        logger.error(HttpStatus.BAD_REQUEST, exception);
        return new ErrorResponse(errorMessage, String.valueOf(HttpStatus.BAD_REQUEST.value()));
    }*/

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
