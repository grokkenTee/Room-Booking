package com.example.bookingroom.mapper;

import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.entity.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {
    RoomDto toRoomDto(Room room);

    Room toRoomEntity(RoomDto roomDto);

    //TODO for mapping list
    List<RoomDto> toRoomDtos(List<Room> rooms);
}
