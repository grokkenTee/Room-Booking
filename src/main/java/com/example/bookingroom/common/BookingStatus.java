package com.example.bookingroom.common;

public enum BookingStatus {
    PROCESSING("Processing"),
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    COMPLETE("Complete"),
    CANCELED("Canceled");

    private final String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public String getString(){
        return status;
    }
}
