package com.example.bookingroom.repository.springJpa;

import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.entity.Room;
import com.example.bookingroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoomServiceImpl implements RoomService {
    @Autowired


    @Override
    public List<Room> getAllRoom() {
        return null;
    }

    @Override
    public Room getRoom(String roomId) {
        return null;
    }

    @Override
    public Room createRoom(Room room) {
        return null;
    }

    @Override
    public Room modifyRoom(String roomId, Room room) {
        return null;
    }

    @Override
    public void deleteRoom(Room room) {

    }

    @Override
    public List<Booking> getListOfBooking(Room room) {
        return null;
    }
}
