package com.example.bookingroom.common;

public final class Endpoint {
    public static final String ROOM_INDEX = "/room";
    public static final String ROOM_DETAIL = "/room/detail/{roomCode}";
    public static final String ROOM_CREATE = "/room/add";
    public static final String ROOM_SAVE = "/room/save";
    public static final String ROOM_DELETE = "/room/delete/{roomCode}";

    private Endpoint() {
    }
}
