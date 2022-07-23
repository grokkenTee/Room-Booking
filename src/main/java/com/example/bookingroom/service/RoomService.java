package com.example.bookingroom.service;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.error.RoomNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoomService {
    //todo throw ex
    Page<RoomDto> searchRoom(RoomType type, RoomStatus status, Integer minSize, Integer maxSize, Pageable pageable);

    Optional<RoomDto> getRoom(String roomCode) throws RoomNotFoundException;

    Optional<RoomDto> getRoom(Long id) throws RoomNotFoundException;

    //TODO throw ex
    RoomDto createRoom(RoomDto room);

    //TODO throw ex
    RoomDto modifyRoom(RoomDto room);

    //TODO throw ex
    void deleteRoom(RoomDto room);

    Boolean isExists(String roomCode);

    Optional<List<Booking>> getListOfBooking(RoomDto roomDto);

}
