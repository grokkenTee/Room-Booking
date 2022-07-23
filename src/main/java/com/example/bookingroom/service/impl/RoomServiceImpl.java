package com.example.bookingroom.service.impl;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.entity.Room;
import com.example.bookingroom.exception.RoomNotFoundException;
import com.example.bookingroom.mapper.RoomMapper;
import com.example.bookingroom.repository.RoomRepository;
import com.example.bookingroom.service.RoomService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    private RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    @Override
    public Page<RoomDto> searchRoom(RoomType type, RoomStatus status, Integer minSize, Integer maxSize, Pageable pageable) {
        Page<Room> entityPage = roomRepository.findAllByTypeAndStatusAndCapacityBetween(type, status, minSize, maxSize, pageable);
        return entityPage
                .map(room -> roomMapper.toRoomDto(room));
    }

    @Override
    public RoomDto getRoom(String roomCode) throws RoomNotFoundException {
        return roomRepository.findByRoomCode(roomCode)
                .map(roomMapper::toRoomDto)
                .orElseThrow(() -> new RoomNotFoundException(roomCode));
    }

    @Override
    public RoomDto getRoom(Long id) throws RoomNotFoundException{
        return roomRepository.findById(id)
                .map(roomMapper::toRoomDto)
                .orElseThrow(() -> new RoomNotFoundException("Room id column not found " + id));
    }

    @Override
    public RoomDto createRoom(RoomDto roomDtoIn) throws Exception {
        if (isExists(roomDtoIn.getRoomCode())) {
            throw new Exception("Room is exsisted");
        }
        Room roomToCreate = roomRepository.save(roomMapper.toRoomEntity(roomDtoIn));
        return roomMapper.toRoomDto(roomToCreate);
    }

    @Override
    public RoomDto updateRoom(RoomDto roomDtoIn) throws IllegalArgumentException{
        Room roomToMod = roomRepository.findByRoomCode(roomDtoIn.getRoomCode()).orElse(null);
        if ((roomDtoIn.getId()) == null || !(roomDtoIn.getId().equals(roomToMod.getId())))
            throw new IllegalArgumentException("Illegal id: id= "+ roomDtoIn.getId() +" not matching with id in record");

        roomToMod.setType(roomDtoIn.getType());
        roomToMod.setStatus(roomDtoIn.getStatus());
        roomToMod.setCapacity(roomDtoIn.getCapacity());
        roomToMod.setImageUrl(roomDtoIn.getImageUrl());
        roomToMod.setDescription(roomDtoIn.getDescription());

        roomToMod = roomRepository.save(roomToMod);
        return roomMapper.toRoomDto(roomToMod);
    }

    @Override
    public void deleteRoom(RoomDto room) {
        //TODO check lại dữ liệu?
        roomRepository.deleteById(room.getId());
    }

    @Override
    public Boolean isExists(String roomCode) {
        return roomRepository.existsByRoomCode(roomCode);
    }

    @Override
    public List<Booking> getListOfBooking(RoomDto roomDto) {
        return roomRepository.findByRoomCode(roomDto.getRoomCode())
                .map(Room::getBookings)
                .orElse(null);
    }
}
