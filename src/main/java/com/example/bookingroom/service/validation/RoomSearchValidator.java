package com.example.bookingroom.service.validation;

import com.example.bookingroom.dto.RoomSearchResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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

        if (Objects.nonNull(searchResult.getMaxCap()) && Objects.nonNull(searchResult.getMinCap()))
            if (searchResult.getMaxCap() < searchResult.getMinCap())
                errors.rejectValue("maxCap", "Logical.order.min-max");
    }
}
