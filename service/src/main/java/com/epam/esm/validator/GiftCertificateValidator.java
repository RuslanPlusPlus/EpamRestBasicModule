package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionDetail;
import com.epam.esm.exception.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GiftCertificateValidator {
    private static final String NAME_REGEX = "\\w[\\w\\s]{2,46}";
    private static final String DESCRIPTION_REGEX = "[\\w\\p{Punct}][\\w\\p{Punct}\\s]{2,101}";
    private static final BigDecimal MIN_PRICE_VALUE = new BigDecimal(0.1);
    private static final BigDecimal MAX_PRICE_VALUE = new BigDecimal(10000L);
    private static final Integer MIN_DURATION_VALUE = 1;
    private static final Integer MAX_DURATION_VALUE = 100;

    private final TagValidator tagValidator;

    @Autowired
    public GiftCertificateValidator(TagValidator tagValidator){
        this.tagValidator = tagValidator;
    }

    public List<ExceptionDetail> validateCreation(GiftCertificateDto certificateDto){
        List<ExceptionDetail> exceptionDetails = new ArrayList<>();
        String name = certificateDto.getName();
        if (validateEmptyName(name).isPresent()){
            exceptionDetails.add(validateEmptyName(name).get());
        }else if (validateName(name).isPresent()){
            exceptionDetails.add(validateName(name).get());
        }

        String description = certificateDto.getDescription();
        if (validateEmptyDescription(description).isPresent()){
            exceptionDetails.add(validateEmptyDescription(description).get());
        }else if (validateDescription(description).isPresent()){
            exceptionDetails.add(validateDescription(description).get());
        }

        BigDecimal price = certificateDto.getPrice();
        if (validateEmptyPrice(price).isPresent()){
            exceptionDetails.add(validateEmptyPrice(price).get());
        }else if (validatePrice(price).isPresent()){
            exceptionDetails.add(validatePrice(price).get());
        }

        Integer duration = certificateDto.getDuration();
        if (validateEmptyDuration(duration).isPresent()){
            exceptionDetails.add(validateEmptyDuration(duration).get());
        }else if (validateDuration(duration).isPresent()){
            exceptionDetails.add(validateDuration(duration).get());
        }

        List<ExceptionDetail> tagsException = tagValidator.validateCertificateTags(certificateDto.getTags());
        if (!tagsException.isEmpty()){
            exceptionDetails.addAll(tagsException);
        }

        return exceptionDetails;

    }

    public List<ExceptionDetail> validateUpdate(GiftCertificateDto certificateDto){
        List<ExceptionDetail> exceptionDetails = new ArrayList<>();
        String name = certificateDto.getName();
        if (name != null){
            if (validateName(name).isPresent()){
                exceptionDetails.add(validateName(name).get());
            }
        }

        String description = certificateDto.getDescription();
        if (description != null){
            if (validateDescription(description).isPresent()){
                exceptionDetails.add(validateDescription(description).get());
            }
        }

        BigDecimal price = certificateDto.getPrice();
        if (price != null){
            if (validatePrice(price).isPresent()){
                exceptionDetails.add(validatePrice(price).get());
            }
        }

        Integer duration = certificateDto.getDuration();
        if (duration != null){
            if (validateDuration(duration).isPresent()){
                exceptionDetails.add(validateDuration(duration).get());
            }
        }

        List<ExceptionDetail> tagsException = tagValidator.validateCertificateTags(certificateDto.getTags());
        if (!tagsException.isEmpty()){
            exceptionDetails.addAll(tagsException);
        }

        return exceptionDetails;
    }

    private Optional<ExceptionDetail> validateEmptyName(String name){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (name == null || name.isBlank()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.EMPTY_NAME,
                    ErrorCode.GC_NAME_EMPTY.getErrorCode(),
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
                    ErrorCode.GC_NAME_NOT_CORRECT.getErrorCode(),
                    name
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }

    private Optional<ExceptionDetail> validateEmptyDescription(String description){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (description == null || description.isBlank()){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.EMPTY_DESCRIPTION,
                    ErrorCode.GC_DESCRIPTION_EMPTY.getErrorCode(),
                    ""
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }

    private Optional<ExceptionDetail> validateDescription(String description){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (!description.matches(DESCRIPTION_REGEX)){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_DESCRIPTION,
                    ErrorCode.GC_DESCRIPTION_NOT_CORRECT.getErrorCode(),
                    description
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }

    private Optional<ExceptionDetail> validateEmptyPrice(BigDecimal price){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (price == null){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.EMPTY_PRICE,
                    ErrorCode.GC_PRICE_EMPTY.getErrorCode(),
                    ""
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }

    private Optional<ExceptionDetail> validatePrice(BigDecimal price){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (MIN_PRICE_VALUE.compareTo(price) > 0 || MAX_PRICE_VALUE.compareTo(price) < 0){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_PRICE,
                    ErrorCode.GC_PRICE_NOT_CORRECT.getErrorCode(),
                    String.valueOf(price)
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }

    private Optional<ExceptionDetail> validateEmptyDuration(Integer duration){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (duration == null){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.EMPTY_DURATION,
                    ErrorCode.GC_DURATION_EMPTY.getErrorCode(),
                    ""
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }

    private Optional<ExceptionDetail> validateDuration(Integer duration){
        Optional<ExceptionDetail> exceptionDetailOptional = Optional.empty();
        if (duration < MIN_DURATION_VALUE || duration > MAX_DURATION_VALUE){
            ExceptionDetail exceptionDetail = new ExceptionDetail(
                    ResponseMessage.INCORRECT_DURATION,
                    ErrorCode.GC_DURATION_NOT_CORRECT.getErrorCode(),
                    String.valueOf(duration)
            );
            exceptionDetailOptional = Optional.of(exceptionDetail);
        }
        return exceptionDetailOptional;
    }

}
