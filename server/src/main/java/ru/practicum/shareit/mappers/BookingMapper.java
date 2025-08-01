package ru.practicum.shareit.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.dto.BookingCreateDto;
import ru.practicum.shareit.dto.BookingDto;
import ru.practicum.shareit.models.Booking;


@Mapper
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    Booking toBooking(BookingCreateDto bookingCreateDto);

    @Mapping(target = "booker", source = "user")
    BookingDto toBookingDto(Booking booking);
}