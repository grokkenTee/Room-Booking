package com.example.bookingroom.exception.advice;

import com.example.bookingroom.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO xử lí bắt exception ngoài, tránh gây loạn controller
//@ControllerAdvice
public class RoomNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String roomNotFoundAdvice(RoomNotFoundException ex) {
        return ex.getMessage();
    }
}
