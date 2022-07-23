package com.example.bookingroom.mapper;

import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {
    RoomDto entityToDto(Room room);

    Room dtoToEntity(RoomDto roomDto);
}
