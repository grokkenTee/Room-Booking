package com.example.bookingroom.controller;

import com.example.bookingroom.entity.Room;
import com.example.bookingroom.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/room")
    public ResponseEntity<?> getListRoom() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<?> getRoom(
            @PathVariable("id") String roomId) {
        return ResponseEntity.ok(roomRepository.findByRoomId(roomId));
    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(Room room) {
        return ResponseEntity.ok(roomRepository.save(room));
    }

    @PutMapping("/room/{id}")
    public ResponseEntity<?> modifyRoom(
            @PathVariable("id") String roomId, Room room) {
        //TODO viết thêm các class custom exception
        Room roomToModify = roomRepository.findByRoomId(roomId).get();
        //TODO viết lại test chuẩn hơn
        if (room.getRoomId().equals(roomToModify.getRoomId())) {
            return ResponseEntity.ok(roomRepository.save(roomToModify));
        }
        return ResponseEntity.badRequest().body("Wrong room Id");
    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(
            @PathVariable(name = "id") String roomId) {
        Room roomToDelete = roomRepository.findByRoomId(roomId).get();

        //TODO viết lại test chuẩn hơn
        if (null != roomToDelete) {
            roomRepository.delete(roomToDelete);
            return ResponseEntity.ok().body("Delete room successfully");
        }
        //TODO viết thêm các class custom exception
        return ResponseEntity.badRequest().body("Wrong room Id");
    }
}
