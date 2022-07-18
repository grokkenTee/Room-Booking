package com.example.bookingroom.error.advice;

import com.example.bookingroom.error.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RoomNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String roomNotFoundAdvice(RoomNotFoundException ex) {
        return ex.getMessage();
    }
}
