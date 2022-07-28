package com.example.bookingroom.dto;

import com.example.bookingroom.common.BookingStatus;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    //TODO id nên để 1 kiểu random nào đó -> khó có thể mò ra để get thông tin.
    private Long id;

    @NotEmpty(message = "Room code not null please!")
    private String roomCode;

    @NotNull(message = "Start time not null please!")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @NotNull(message = "End time not null please")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    private BookingStatus status;
    private String description;
}
