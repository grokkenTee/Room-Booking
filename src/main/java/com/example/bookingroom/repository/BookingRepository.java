package com.example.bookingroom.repository;

import com.example.bookingroom.common.BookingStatus;
import com.example.bookingroom.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query("select b from Booking b" +
            " where (:roomCode is null or :roomCode = '' or b.room.roomCode = :roomCode)" +
            " and (:status is null or b.status = :status)" +
            //nếu xử lí ngoài kia thì 2 thằng Time chắc chắn k null -> có thể bỏ
            " and (((:fromTime is null) and (:toTime is null))" +
            "    or ((b.startTime > :fromTime) and (b.endTime < :toTime)))")
    Page<Booking> searchByCondition(String roomCode, BookingStatus status,
                                    LocalDateTime fromTime, LocalDateTime toTime, Pageable pageable);

    Optional<Booking> findById(Long id);

    @Query("select (count(b) > 0) from Booking b" +
            " where b.room.roomCode = :roomCode" +
            " and b.endTime > :fromTime" +
            " and b.startTime < :toTime" +
            " and b.status = :status")
    boolean existsByCondition(String roomCode, LocalDateTime fromTime,
                              LocalDateTime toTime, BookingStatus status);
}