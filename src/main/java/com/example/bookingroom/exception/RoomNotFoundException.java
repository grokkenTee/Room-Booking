package com.example.bookingroom.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(String roomCode) {
        super("Could not find room code " + roomCode);
    }
}
