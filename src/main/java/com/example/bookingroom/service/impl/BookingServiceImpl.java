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

    //Validate for change state of booking
    @Override
    public void validateBusinessLogic(BookingDto bookingDto) throws Exception {
        LocalDateTime startTime = bookingDto.getStartTime();
        LocalDateTime endTime = bookingDto.getEndTime();
        String roomCode = bookingDto.getRoomCode();
        BookingStatus statusToChange = bookingDto.getStatus();

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Wrong fromTime - toTime input");
        }
        //only check if change status -> ACCEPTED
        if (BookingStatus.ACCEPTED.equals(statusToChange)) {
            if (isInUseBetweenTime(roomCode, startTime, endTime)) {
                throw new Exception("Room " + roomCode + " already busy in this time");
            }
        }
    }

    @Override
    public Boolean isInUseBetweenTime(String roomCode, LocalDateTime fromTime, LocalDateTime toTime) {
        return bookingRepository
                .existsByCondition(roomCode, fromTime, toTime, BUSY_STATUS);
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
    public Page<BookingDto> searchBooking(String roomCode, BookingStatus status, LocalDateTime fromTime, LocalDateTime toTime, Pageable pageRequest) {
        return bookingRepository
                .searchByCondition(roomCode, status, fromTime, toTime, pageRequest)
                .map(mapper::toBookingDto);
    }

    private Booking getBookingEntity(Long id) throws Exception {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new Exception("Wrong booking id"));
    }

    @Override
    public BookingDto getBooking(Long id) throws Exception {
        return bookingRepository.findById(id)
                .map(mapper::toBookingDto)
                .orElseThrow(() -> new Exception("Wrong booking id"));
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto) throws Exception {
        validateBusinessLogic(bookingDto);

        Booking bookingToSave = mapper.toBookingEntity(bookingDto);
        Room room = roomRepository
                .findByRoomCode(bookingDto.getRoomCode())
                .orElseThrow(() -> new RoomNotFoundException("Wrong roomCode"));
        bookingToSave.setRoom(room);

        return mapper.toBookingDto(bookingRepository.save(bookingToSave));
    }

    @Override
    public BookingDto modifyBooking(BookingDto bookingDto) throws Exception {
        //TODO chuyển thêm status vào để check.
        validateBusinessLogic(bookingDto);

        Booking bookingToMod = getBookingEntity(bookingDto.getId());
        Room room = roomRepository
                .findByRoomCode(bookingDto.getRoomCode())
                .orElseThrow(() -> new RoomNotFoundException("Wrong roomCode"));

        bookingToMod.setRoom(room);
        bookingToMod.setStartTime(bookingDto.getStartTime());
        bookingToMod.setEndTime(bookingDto.getEndTime());
        bookingToMod.setStatus(bookingDto.getStatus());
        bookingToMod.setDescription(bookingDto.getDescription());

        return mapper.toBookingDto(bookingRepository.save(bookingToMod));
    }

    @Override
    public void deleteBooking(BookingDto bookingDto) throws Exception {
        if (bookingDto.getStatus() != BookingStatus.CANCELED)
            throw new Exception("Can only delete booking with status is CANCELED");
        else {
            Booking bookingToDel = bookingRepository.findById(bookingDto.getId()).orElseThrow();
            bookingRepository.delete(bookingToDel);
        }
    }

    @Override
    public void updateBookingStatus(Long id, BookingStatus bookingStatus) throws Exception {
        Booking bookingToUpdate = getBookingEntity(id);

        String roomCode = bookingToUpdate.getRoom().getRoomCode();
        LocalDateTime fromTime = bookingToUpdate.getStartTime();
        LocalDateTime toTime = bookingToUpdate.getEndTime();

        if (isInUseBetweenTime(roomCode, fromTime, toTime)) {
            throw new Exception("Room " + roomCode + " already busy in this time");
        } else {
            bookingToUpdate.setStatus(bookingStatus);
            bookingRepository.save(bookingToUpdate);
        }
    }
}
