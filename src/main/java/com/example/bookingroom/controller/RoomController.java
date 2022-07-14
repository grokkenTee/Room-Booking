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

    @GetMapping("/room/{code}")
    public ResponseEntity<?> getRoom(
            @PathVariable("code") String roomCode) {
        return ResponseEntity.ok(roomRepository.findByRoomCode(roomCode));
    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(Room room) {
        return ResponseEntity.ok(roomRepository.save(room));
    }

    @PutMapping("/room/{code}")
    public ResponseEntity<?> modifyRoom(
            @PathVariable("code") String roomCode,
            Room room) {
        //TODO viết thêm các class custom exception
        Room roomToModify = roomRepository.findByRoomCode(roomCode).get();
        //TODO viết lại test chuẩn hơn
        if (room.getRoomCode().equals(roomToModify.getRoomCode())) {
            return ResponseEntity.ok(roomRepository.save(roomToModify));
        }
        return ResponseEntity.badRequest().body("Wrong room Id");
    }

    @DeleteMapping("/room/{code}")
    public ResponseEntity<?> deleteRoom(
            @PathVariable(name = "code") String roomCode) {
        Room roomToDelete = roomRepository.findByRoomCode(roomCode).get();
        //TODO viết lại test chuẩn hơn
        if (null != roomToDelete) {
            roomRepository.delete(roomToDelete);
            return ResponseEntity.ok().body("Delete room successfully");
        }
        //TODO viết thêm các class custom exception
        return ResponseEntity.badRequest().body("Wrong room Id");
    }
}
