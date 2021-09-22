package com.epam.esm.validator;

import com.epam.esm.exception.*;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class PaginationValidator {
    private static final int pageNumberMinValue = 1;
    private static final int pageSizeMinValue = 1;
    private static final int pageSizeMaxValue = 100;

    public void validatePageNumber(int page){
        if (page < pageNumberMinValue){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_PAGE_NUMBER,
                    ErrorCode.INCORRECT_PAGE_NUMBER.getErrorCode(),
                    String.valueOf(page)
            );
            throw new IncorrectParamException(Collections.singletonList(exceptionDetail));
        }
    }

    public void validateSize(int size){
        if (size < pageSizeMinValue || size > pageSizeMaxValue){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_PAGE_SIZE,
                    ErrorCode.INCORRECT_PAGE_SIZE.getErrorCode(),
                    String.valueOf(size)
            );
            throw new IncorrectParamException(Collections.singletonList(exceptionDetail));
        }
    }

    public void checkIfPageExists(int page, long pageAmount){
        if (page > pageAmount){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.PAGE_NOT_FOUND,
                    ErrorCode.PAGE_NOT_FOUND.getErrorCode(),
                    String.valueOf(page)
            );
            throw new ResourceNotFoundException(exceptionDetail);
        }
    }

}
