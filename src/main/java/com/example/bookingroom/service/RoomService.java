package com.example.bookingroom.service;

import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.entity.Room;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    List<Room> getAllRoom();

    Room getRoom(String roomId);

    Room createRoom(Room room);

    Room modifyRoom(String roomId, Room room);

    void deleteRoom(Room room);

    List<Booking> getListOfBooking(Room room);

}
