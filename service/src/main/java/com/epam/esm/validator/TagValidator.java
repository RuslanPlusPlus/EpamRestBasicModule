package com.epam.esm.validator;

import org.springframework.stereotype.Component;

@Component
public class TagValidator {
    private static final String NAME_REGEX = "(\\w|\\s|\\d){3,45}";
    private static final int TAG_ID_MIN_VALUE = 1;

    public boolean validateId(long id){
        return id >= TAG_ID_MIN_VALUE;
    }

    public boolean validateName(String name){
        return name != null && !name.isBlank() && !name.matches(NAME_REGEX);
    }


}
