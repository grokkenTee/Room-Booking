package com.example.bookingroom.mapper;

import com.example.bookingroom.dto.BookingDto;
import com.example.bookingroom.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface BookingMapper {

    Booking toBookingEntity(BookingDto bookingDto);

    @Mapping(target = "roomCode", source = "booking.room.roomCode")
    BookingDto toBookingDto(Booking booking);

    List<BookingDto> toBookingDtos(List<Booking> bookings);

}
