package com.example.bookingroom.common;

public enum RoomType {
    DINNING_ROOM("Dinning room"),
    MEETING_ROOM("Meeting room"),
    CASUAL_ROOM("Casual room"),
    OFFICE_ROOM("Office room");

    private final String type;

    RoomType(String type) {
        this.type = type;
    }

    public String getString() {
        return type;
    }
}
