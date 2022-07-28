package com.example.bookingroom.service;

import com.example.bookingroom.common.BookingStatus;
import com.example.bookingroom.dto.BookingDto;
import com.example.bookingroom.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface BookingService {
    Boolean isInUseBetweenTime(String roomCode, LocalDateTime fromTime, LocalDateTime toTime);

    Page<BookingDto> searchBooking(String roomCode, BookingStatus status, LocalDateTime fromTime, LocalDateTime toTime, Pageable pageable);

    List<BookingDto> getAllBooking();

    List<Booking> getAllBookingApi();

    BookingDto getBooking(Long id) throws Exception;

    BookingDto createBooking(BookingDto bookingDto, String roomCode) throws Exception;

    BookingDto updateBooking(BookingDto bookingDto);

    void deleteBooking(BookingDto bookingDto);
}
