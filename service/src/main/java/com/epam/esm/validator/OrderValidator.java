package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionDetail;
import com.epam.esm.exception.IncorrectParamException;
import com.epam.esm.exception.ResponseMessage;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderValidator {
    private static final int ID_MIN_VALUE = 1;

    public void validateOrder(OrderDto orderDto){

        Long userId = orderDto.getUserId();
        if (userId == null){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.EMPTY_USER_ID,
                    ErrorCode.USER_ID_EMPTY.getErrorCode(),
                    ""
            );
            throw new IncorrectParamException(Collections.singletonList(exceptionDetail));
        }else if (userId < ID_MIN_VALUE){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_USER_ID,
                    ErrorCode.USER_ID_INCORRECT.getErrorCode(),
                    String.valueOf(userId)
            );
            throw new IncorrectParamException(Collections.singletonList(exceptionDetail));
        }

        validateCertificateIds(orderDto.getCertificates());
    }

    private void validateCertificateIds(List<GiftCertificateDto> certificateDtoList){
        if (certificateDtoList == null || certificateDtoList.isEmpty()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.ORDER_CERTIFICATES_EMPTY,
                    ErrorCode.ORDER_EMPTY_CERTIFICATES.getErrorCode(),
                    ""
            );
            throw new IncorrectParamException(Collections.singletonList(exceptionDetail));
        }
        certificateDtoList.forEach(
                certificateDto -> {
                    if (certificateDto.getId() == null){
                        ExceptionDetail exceptionDetail = new ExceptionDetail(
                                ResponseMessage.EMPTY_CERTIFICATE_ID,
                                ErrorCode.GC_ID_EMPTY.getErrorCode(),
                                ""
                        );
                        throw new IncorrectParamException(Collections.singletonList(exceptionDetail));
                    }

                    if (certificateDto.getId() < ID_MIN_VALUE){
                        ExceptionDetail exceptionDetail = new ExceptionDetail(
                                ResponseMessage.INCORRECT_CERTIFICATE_ID,
                                ErrorCode.GC_ID_INCORRECT.getErrorCode(),
                                String.valueOf(certificateDto.getId())
                        );
                        throw new IncorrectParamException(Collections.singletonList(exceptionDetail));
                    }
                }
        );
    }

}
