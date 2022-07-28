package com.example.bookingroom.repository;

import com.example.bookingroom.common.BookingStatus;
import com.example.bookingroom.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findAllByStatus(BookingStatus status, Pageable pageable);

    Optional<Booking> findById(Long id);


    @Query("select (count(b) > 0) from Booking b " +
            "where b.room.roomCode = :roomCode and b.endTime > :fromTime and b.startTime < :toTime and b.status = :status")
    boolean existsByRoom_RoomCodeAndEndTimeAfterAndStartTimeBeforeAndStatus(@Param("roomCode") String roomCode, @Param("fromTime") LocalDateTime fromTime, @Param("toTime") LocalDateTime toTime, @Param("status") BookingStatus status);
}