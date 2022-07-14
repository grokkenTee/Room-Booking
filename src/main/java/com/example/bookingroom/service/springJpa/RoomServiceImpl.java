package com.example.bookingroom.service.springJpa;

import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.entity.Room;
import com.example.bookingroom.repository.RoomRepository;
import com.example.bookingroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoom(String roomId) {
        //TODO chỗ Optional ni xử lí như nào?
        return roomRepository.findByRoomCode(roomId).get();
    }

    @Override
    public Room createRoom(Room room) {
        if (roomRepository.existsByRoomCode(room.getRoomCode())){
            //TODO nên throw Exception?
            return null;
        }
        return roomRepository.save(room);
    }

    @Override
    public Room modifyRoom(String roomId, Room room) {
        Room roomToModify = roomRepository.findByRoomCode(roomId).get();
        if (null == roomToModify){
            return null;
        }
        //TODO viết hàm clone() hay dùng builder patern ?
        //TODO nên save luôn room hay get(roomId) rồi gán mới save(roomToModify)
        //TODO viết convert sang dto luôn
        //Xử lí modifiedAt ở BaseEntity
        roomToModify.setCapacity(room.getCapacity());
        roomToModify.setRoomType(room.getRoomType());


        return roomRepository.save(roomToModify);
    }

    @Override
    public void deleteRoom(Room room) {

    }

    @Override
    public List<Booking> getListOfBooking(Room room) {
        return null;
    }
}
