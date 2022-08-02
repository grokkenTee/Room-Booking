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
    void validateBusinessLogic(BookingDto bookingDto) throws Exception;

    Boolean isInUseBetweenTime(String roomCode, LocalDateTime fromTime, LocalDateTime toTime);

    Page<BookingDto> searchBooking(String roomCode, BookingStatus status, LocalDateTime fromTime, LocalDateTime toTime, Pageable pageRequest);

    List<BookingDto> getAllBooking();

    List<Booking> getAllBookingApi();

    BookingDto getBooking(Long id) throws Exception;

    BookingDto createBooking(BookingDto bookingDto) throws Exception;

    BookingDto modifyBooking(BookingDto bookingDto) throws Exception;

    void deleteBooking(BookingDto bookingDto) throws Exception;

    void updateBookingStatus(Long id, BookingStatus bookingStatus) throws Exception;
}
