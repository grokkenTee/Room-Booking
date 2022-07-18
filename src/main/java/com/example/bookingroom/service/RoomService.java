package com.example.bookingroom.service;

import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.entity.Room;
import com.example.bookingroom.error.RoomNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    List<Room> getAllRoom();

    Room getRoom(String roomId) throws RoomNotFoundException;

    Room createRoom(Room room);

    Room modifyRoom(String roomId, Room room);

    void deleteRoom(Room room);

    List<Booking> getListOfBooking(Room room) throws RoomNotFoundException;

}