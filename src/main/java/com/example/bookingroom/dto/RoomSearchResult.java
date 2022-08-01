package com.example.bookingroom.dto;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomSearchResult {
    private RoomType type = null;
    private RoomStatus status = null;

    @NotNull(message = "MinCap must not be null please!")
    private Integer minCap = 0;

    @NotNull(message = "MaxCap must not be null please!")
    private Integer maxCap = 99999;

    private Integer page = 1;
    private Integer size = 10;
    private Long numsOfRecord = 0L;
    private Integer numsOfPage = 0;

}
