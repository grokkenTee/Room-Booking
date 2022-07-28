package com.example.bookingroom.service.validation;

import com.example.bookingroom.dto.RoomSearchResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class RoomSearchValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return RoomSearchResult.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RoomSearchResult searchResult = (RoomSearchResult) o;

        if (Objects.isNull(searchResult.getMinCap()))
            ValidationUtils.rejectIfEmpty(errors, "minCap", "NotEmpty");
        else if (searchResult.getMinCap() < 0)
            errors.rejectValue("minCap", "Negative");

        if (Objects.isNull(searchResult.getMaxCap()))
            ValidationUtils.rejectIfEmpty(errors, "maxCap", "NotEmpty");
        else {
            if (searchResult.getMaxCap() < 0)
                errors.rejectValue("maxCap", "Negative");
            if (searchResult.getMaxCap() < searchResult.getMinCap())
                errors.rejectValue("maxCap", "Logical.minMax.order");
        }
    }
}
