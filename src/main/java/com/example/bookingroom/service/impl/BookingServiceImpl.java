package com.example.bookingroom.service.impl;

import com.example.bookingroom.common.BookingStatus;
import com.example.bookingroom.dto.BookingDto;
import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.entity.Room;
import com.example.bookingroom.exception.RoomNotFoundException;
import com.example.bookingroom.mapper.BookingMapper;
import com.example.bookingroom.repository.BookingRepository;
import com.example.bookingroom.repository.RoomRepository;
import com.example.bookingroom.service.BookingService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    //TODO sau này chuyển sang list nếu muốn busy là 1 vài status
    private static final BookingStatus BUSY_STATUS = BookingStatus.ACCEPTED;
    private final BookingMapper mapper = Mappers.getMapper(BookingMapper.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Boolean isInUseBetweenTime(String roomCode, LocalDateTime fromTime, LocalDateTime toTime) {
        return bookingRepository
                .existsByRoom_RoomCodeAndEndTimeAfterAndStartTimeBeforeAndStatus(roomCode, fromTime, toTime, BUSY_STATUS);
    }

    @Override
    public List<BookingDto> getAllBooking() {
        return mapper.toBookingDtos(bookingRepository.findAll());
    }

    @Override
    public List<Booking> getAllBookingApi() {
        return bookingRepository.findAll();
    }

    @Override
    public Page<BookingDto> searchBooking(String roomCode, BookingStatus status, LocalDateTime fromTime, LocalDateTime toTime, Pageable pageable) {
        return bookingRepository
                .findAllByStatus(status, pageable)
                .map(mapper::toBookingDto);
    }

    @Override
    public BookingDto getBooking(Long id) throws Exception {
        return bookingRepository.findById(id)
                .map(mapper::toBookingDto)
                .orElseThrow(() -> new Exception("Id mismatch with DB record"));
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto, String roomCode) throws Exception {
        LocalDateTime startTime = bookingDto.getStartTime();
        LocalDateTime endTime = bookingDto.getEndTime();

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Wrong fromTime-toTime input");
        } else if (isInUseBetweenTime(roomCode, startTime, endTime)) {
            throw new Exception("Room " + roomCode + " already busy in this time");
        } else {
            Booking bookingToSave = mapper.toBookingEntity(bookingDto);
            Room room = roomRepository
                    .findByRoomCode(roomCode)
                    .orElseThrow(() -> new RoomNotFoundException("Wrong roomCode"));
            bookingToSave.setRoom(room);
            //TODO lỏng cái dependency ra đi
            bookingToSave.setStatus(BookingStatus.ACCEPTED);

            return mapper.toBookingDto(bookingRepository.save(bookingToSave));
        }
    }

    //TODO chưa xử lí
    @Override
    public BookingDto updateBooking(BookingDto bookingDto) {
        return null;
    }

    @Override
    public void deleteBooking(BookingDto bookingDto) {
        Booking bookingToDel = bookingRepository
                .findById(bookingDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Wrong booking id"));

        bookingToDel.setStatus(BookingStatus.CANCELED);
        bookingRepository.save(bookingToDel);
    }
}
