package com.example.bookingroom.dto;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;

    @NotEmpty(message = "Room code can not be empty please!")
    private String roomCode;

    @NotNull(message = "Capacity not null please!")
    @Min(value = 0, message = "Capacity must be positive please!")
    private Integer capacity;

    private RoomType type;

    private RoomStatus status;

    private String description;

    private String imageUrl;
}
