package com.example.bookingroom.repository;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByBookingId(Long bookingId);

    Optional<Booking> findAllByStatus(RoomStatus status);
}
