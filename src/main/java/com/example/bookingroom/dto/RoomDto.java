package com.example.bookingroom.dto;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;

    private String roomCode;

    private Integer capacity;

    private RoomType type;

    private RoomStatus status;

    private String description;

    private String imageUrl;
}
