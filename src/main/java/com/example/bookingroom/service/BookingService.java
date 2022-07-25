package com.example.bookingroom.service;

import com.example.bookingroom.common.BookingStatus;
import com.example.bookingroom.dto.BookingDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookingService {
    Page<BookingDto> searchBooking(BookingStatus status, Pageable pageable);

    Optional<BookingDto> getBooking(Long id);

    BookingDto createBooking(BookingDto bookingDto);

    BookingDto updateBooking(BookingDto bookingDto);

    void deleteBooking(BookingDto bookingDto);
}
