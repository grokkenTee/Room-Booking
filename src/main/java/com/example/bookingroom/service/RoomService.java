package com.example.bookingroom.service;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.exception.RoomNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    //todo throw ex
    Page<RoomDto> searchRoom(RoomType type, RoomStatus status, Integer minSize, Integer maxSize, Pageable pageable);

    RoomDto getRoom(String roomCode) throws RoomNotFoundException;

    RoomDto getRoom(Long id) throws RoomNotFoundException;

    RoomDto createRoom(RoomDto room) throws Exception;

    RoomDto updateRoom(RoomDto room) throws IllegalArgumentException;

    //TODO throw ex
    void deleteRoom(RoomDto room);

    Boolean isExists(String roomCode);

    List<Booking> getListOfBooking(RoomDto roomDto);

}
