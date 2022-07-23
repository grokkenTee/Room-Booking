package com.example.bookingroom.repository;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import com.example.bookingroom.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


    @Query("select r from Room r where (:type is null or r.type = :type ) " +
                                " and (:status is null or r.status = :status) " +
                                " and (r.capacity between :min and :max)")
    Page<Room> findAllByTypeAndStatusAndCapacityBetween(RoomType type, RoomStatus status, Integer min, Integer max, Pageable pageable);

    Optional<Room> findByRoomCode(String roomCode);

    Optional<Room> findAllByType(RoomType type);

    boolean existsByRoomCode(String roomCode);


}
