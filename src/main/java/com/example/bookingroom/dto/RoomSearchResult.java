package com.example.bookingroom.dto;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;

public class RoomSearchResult {
    public final RoomType type;
    public final RoomStatus status;
    public final Integer minCap;
    public final Integer maxCap;
    public final Integer page;
    public final Integer size;
    public final Long numsOfRecord;
    public final Integer numsOfPage;

    public RoomSearchResult(RoomType type, RoomStatus status, Integer minCap, Integer maxCap, Integer page, Integer size, Long numsOfRecord, Integer numsOfPage) {
        this.type = type;
        this.status = status;
        this.minCap = minCap;
        this.maxCap = maxCap;
        this.page = page;
        this.size = size;
        this.numsOfRecord = numsOfRecord;
        this.numsOfPage = numsOfPage;
    }
}
