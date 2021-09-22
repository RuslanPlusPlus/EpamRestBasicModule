package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionDetail;
import com.epam.esm.exception.ResponseMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TagValidator {
    private static final String NAME_REGEX = "\\w[\\w\\s]{2,46}";

    public List<ExceptionDetail> validateTag(TagDto tagDto){
        List<ExceptionDetail> exceptionDetails = new ArrayList<>();
        String name = tagDto.getName();
        if (validateEmptyName(name).isPresent()){
            exceptionDetails.add(validateEmptyName(name).get());
        }else if (validateName(name).isPresent()){
            exceptionDetails.add(validateName(name).get());
        }
        return exceptionDetails;
    }

    public List<ExceptionDetail> validateCertificateTags(List<TagDto> tagDtoList){
        List<ExceptionDetail> exceptionDetails = new ArrayList<>();
        if (tagDtoList != null){
            tagDtoList.forEach(
                    tagDto -> {
                        String name = tagDto.getName();
                        if (validateEmptyName(name).isPresent()){
                            exceptionDetails.add(validateEmptyName(name).get());
                        }else if (validateName(name).isPresent()){
                            exceptionDetails.add(validateName(name).get());
                        }
                    }
            );
        }
        return exceptionDetails;
    }

    private Optional<ExceptionDetail> validateEmptyName(String name){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (name == null || name.isBlank()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.EMPTY_NAME,
                    ErrorCode.TAG_NAME_EMPTY.getErrorCode(),
                    ""
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }

    private Optional<ExceptionDetail> validateName(String name){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (!name.matches(NAME_REGEX)){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_NAME,
                    ErrorCode.TAG_NAME_NOT_CORRECT.getErrorCode(),
                    name
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }
}
