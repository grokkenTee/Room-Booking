package com.example.bookingroom.controller;

import com.example.bookingroom.entity.Room;
import com.example.bookingroom.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoutingController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/room")
    public ResponseEntity<?> getListRoom() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @GetMapping("/room/{?id}")
    public ResponseEntity<?> getRoom(
            @PathVariable(name = "id") Integer roomId){
        return ResponseEntity.ok(roomRepository.findById(roomId));
    }

    @PostMapping("/room")
    public ResponseEntity<?> createRoom(
            @RequestBody Room room){
        return ResponseEntity.ok(roomRepository.save(room));
    }

//    @PutMapping("/room/{?id}")
//    public ResponseEntity<?> modifyRoom(
//            @PathVariable(name = "id") Integer roomId,
//            @RequestBody Room room){
//        //TODO viết thêm các class custom exception
//        Room roomToModify = roomRepository.findById(roomId).get();
//        roomToModify.set
//    }

    @DeleteMapping("/room/{id}")
    public ResponseEntity<?> deleteRoom(
            @PathVariable Integer roomId){
        Room roomToDelete = roomRepository.findById(roomId).get();
        if (null != roomToDelete){
            roomRepository.delete(roomToDelete);
        }
        return ResponseEntity.
    }
}
