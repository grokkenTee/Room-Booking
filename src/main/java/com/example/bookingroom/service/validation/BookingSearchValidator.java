package com.example.bookingroom.service.validation;

import com.example.bookingroom.dto.BookingSearchResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookingSearchValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BookingSearchResult.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookingSearchResult bookingSearchResult = (BookingSearchResult) target;
        var fromTime = bookingSearchResult.getFromTime();
        var toTime = bookingSearchResult.getToTime();

        //quick return common case
        if (fromTime == null && toTime == null)
            ;
        else if (fromTime == null)
            errors.rejectValue("fromTime", "Logical.empty.fromTime");
        else if (toTime == null)
            errors.rejectValue("toTime", "Logical.empty.toTime");
        else
            if (toTime.isBefore(fromTime))
                errors.rejectValue("toTime", "Logical.order.to-from");
    }
}
