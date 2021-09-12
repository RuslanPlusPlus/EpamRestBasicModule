package com.epam.esm.validator;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class GiftCertificateValidator {
    private static final String NAME_REGEX = "(\\w|\\s|\\d){3,45}";
    private static final String DESCRIPTION_REGEX = "(\\w|\\s|\\d){3,100}";
    //private static final int CERTIFICATE_ID_MIN_VALUE = 1;
    private static final BigDecimal MIN_PRICE_VALUE = new BigDecimal(0.1);
    private static final BigDecimal MAX_PRICE_VALUE = new BigDecimal(10000L);
    private static final Integer MIN_DURATION_VALUE = 1;
    private static final Integer MAX_DURATION_VALUE = 100;

    /*public boolean validateId(long id){
        return id >= CERTIFICATE_ID_MIN_VALUE;
    }*/

    public boolean validateName(String name){
        return name != null && !name.isBlank() && name.matches(NAME_REGEX);
    }

    public boolean validateDescription(String description){
        return description != null && !description.isBlank() && description.matches(DESCRIPTION_REGEX);
    }

    public boolean validatePrice(BigDecimal price){
        return price != null && MIN_PRICE_VALUE.compareTo(price) < 0 && MAX_PRICE_VALUE.compareTo(price) >= 0;
    }

    public boolean validateDuration(Integer duration){
        return duration != null && MIN_DURATION_VALUE <= duration && MAX_DURATION_VALUE >= duration;
    }

}
