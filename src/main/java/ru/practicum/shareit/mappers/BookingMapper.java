package ru.practicum.shareit.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.dto.booking.BookingCreateDto;
import ru.practicum.shareit.dto.booking.BookingDto;
import ru.practicum.shareit.dto.booking.BookingDtoSimple;
import ru.practicum.shareit.models.Booking;
import ru.practicum.shareit.repositoryes.ItemRepository;
import ru.practicum.shareit.repositoryes.UserRepository;

@Mapper(componentModel = "spring", uses = UserMapping.class)
public abstract class BookingMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Mapping(target = "item", expression = "java(itemRepository.findById(bookingCreateDto.getItemId()).orElse(null))")
    public abstract Booking fromDto(BookingCreateDto bookingCreateDto);

    @Mapping(target = "booker", source = "booker")
    public abstract BookingDto toDto(Booking booking);

    public abstract BookingDtoSimple toDtoSimple(Booking booking);
}