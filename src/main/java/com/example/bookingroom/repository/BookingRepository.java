package com.example.bookingroom.repository;

import com.example.bookingroom.common.BookingStatus;
import com.example.bookingroom.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Page<Booking> findAllByStatus(BookingStatus status, Pageable pageable);

}