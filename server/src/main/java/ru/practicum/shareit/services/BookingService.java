package ru.practicum.shareit.services;

import ru.practicum.shareit.dto.BookingCreateDto;
import ru.practicum.shareit.enums.BookingState;
import ru.practicum.shareit.models.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(long userId, BookingCreateDto bookingCreateDto);

    Booking getBooking(long userId, long bookingId);

    Booking updateBooking(long userId, long bookingId, boolean approved);

    List<Booking> getBookingsByUser(long userId, BookingState state);

    List<Booking> getBookingsByOwner(long ownerId, BookingState state);

    Booking getLastBooking(long itemId);

    Booking getNextBooking(long itemId);

    boolean existsByBookerIdAndItemId(long bookerId, long itemId);
}