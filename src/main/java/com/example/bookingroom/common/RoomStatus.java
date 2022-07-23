package com.example.bookingroom.common;

public enum RoomStatus {
    AVAILABLE("Available"),
    BUSY("Busy"),
    NOT_AVAILABLE("Not Available");

    private final String status;

    RoomStatus(final String status) {
        this.status = status;
    }

    public String getString(){
        return status;
    }
}