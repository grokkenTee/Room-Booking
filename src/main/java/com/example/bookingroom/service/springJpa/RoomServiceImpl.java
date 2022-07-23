package com.example.bookingroom.service.springJpa;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.entity.Room;
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
import java.util.function.Function;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    private RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);

    @Override
    public Page<RoomDto> searchRoom(RoomType type, RoomStatus status, Integer minSize, Integer maxSize, Pageable pageable) {
        Page<Room> entityPage = roomRepository.findAllByTypeAndStatusAndCapacityBetween(type, status, minSize, maxSize, pageable);
        Page<RoomDto> dtoPage = entityPage.map(new Function<Room, RoomDto>() {
            @Override
            public RoomDto apply(Room room) {
                return roomMapper.entityToDto(room);
            }
        });
        return dtoPage;
    }

    @Override
    public Optional<RoomDto> getRoom(String roomCode) {
        return roomRepository.findByRoomCode(roomCode)
                .map(roomMapper::entityToDto);
    }

    @Override
    public Optional<RoomDto> getRoom(Long id) {
        return roomRepository.findById(id)
                .map(roomMapper::entityToDto);
    }

    @Override
    public RoomDto createRoom(RoomDto roomDtoIn) {
        if (isExists(roomDtoIn.getRoomCode())) {
            //TODO viết room exists exception
            return null;
        }
        Room roomToCreate = roomRepository.save(roomMapper.dtoToEntity(roomDtoIn));
        return roomMapper.entityToDto(roomToCreate);
    }

    @Override
    public RoomDto modifyRoom(RoomDto roomDtoIn) {
        Room roomToMod = roomRepository.findByRoomCode(roomDtoIn.getRoomCode()).get();
        if (roomDtoIn.getId() == null || !(roomDtoIn.getId().equals(roomToMod.getId())))
            //TODO throw Exception
            return null;
        roomToMod.setType(roomDtoIn.getType());
        roomToMod.setStatus(roomDtoIn.getStatus());
        roomToMod.setCapacity(roomDtoIn.getCapacity());
        roomToMod.setImageUrl(roomDtoIn.getImageUrl());
        roomToMod.setDescription(roomDtoIn.getDescription());

        roomToMod = roomRepository.save(roomToMod);
        return roomMapper.entityToDto(roomToMod);
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
    public Optional<List<Booking>> getListOfBooking(RoomDto roomDto) {
        return roomRepository.findByRoomCode(roomDto.getRoomCode())
                .map(Room::getBookings);
    }
}
