package com.example.bookingroom.dto;

import com.example.bookingroom.common.BookingStatus;

public class BookingDto {
    //TODO id nên để 1 kiểu random nào đó -> khó có thể mò ra để get thông tin.
    private Long id;
    private BookingStatus status;
    private String roomCode;
    private String description;
    private String fromTime;
    private String toTime;
}
