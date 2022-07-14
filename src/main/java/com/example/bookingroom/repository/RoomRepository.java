package com.example.bookingroom.repository;

import com.example.bookingroom.common.RoomType;
import com.example.bookingroom.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByRoomId(String roomId);

    Optional<Room> findAllByRoomType(RoomType roomType);

    boolean existsByRoomId(String roomId);
}
