package com.example.bookingroom.service.springJpa;

import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.entity.Room;
import com.example.bookingroom.error.RoomNotFoundException;
import com.example.bookingroom.repository.RoomRepository;
import com.example.bookingroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(String roomCode) throws RoomNotFoundException{
        return roomRepository.findByRoomCode(roomCode)
                .orElseThrow(() -> new RoomNotFoundException(roomCode));
    }

    @Override
    public Room createRoom(Room newRoom) {
        //TODO viết room exists exception
        if (roomRepository.existsByRoomCode(newRoom.getRoomCode())) {
            return null;
        }
        return roomRepository.save(newRoom);
    }

    @Override
    public Room modifyRoom(String roomCode, Room newRoom) {
        return roomRepository.findByRoomCode(roomCode)
                .map(room -> {
                            room.setRoomType(newRoom.getRoomType());
                            room.setCapacity(newRoom.getCapacity());
                            room.setDescription(newRoom.getDescription());
                            room.setImageUrl(newRoom.getImageUrl());
                            return roomRepository.save(room);
                        }
                ).orElseGet(() -> {
                    newRoom.setRoomCode(roomCode);
                    return roomRepository.save(newRoom);
                });
    }

    @Override
    public void deleteRoom(Room room) {
        roomRepository.delete(room);
    }

    @Override
    public List<Booking> getListOfBooking(Room room) throws RoomNotFoundException {
        return roomRepository.findByRoomCode(room.getRoomCode())
                .map(Room::getBookings)
                .orElseThrow(() -> new RoomNotFoundException(room.getRoomCode()));
    }
}
