package ru.practicum.shareit.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.dto.booking.BookingCreateDto;
import ru.practicum.shareit.dto.booking.BookingDto;
import ru.practicum.shareit.enums.TimeStatus;
import ru.practicum.shareit.services.BookingService;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestHeader ("X-Sharer-User-Id") @Positive Long userId,
                                    @Valid @RequestBody BookingCreateDto booking) {
        return bookingService.createBooking(booking, userId);
    }

    @PatchMapping(path = "/{bookingId}")
    public BookingDto approveBooking(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                                     @PathVariable Long bookingId,
                                     @RequestParam boolean approved) {
        return bookingService.approveBooking(bookingId, userId, approved);
    }

    @GetMapping(path = "/{bookingId}")
    public BookingDto getBooking(@PathVariable @Positive Long bookingId) {
        return bookingService.getBooking(bookingId);
    }

    @GetMapping
    public List<BookingDto> getBookingsByBookerId(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                                                  @RequestParam(defaultValue = "ALL") TimeStatus status) {
        return bookingService.getBookingByBookerIdAndStatus(userId, status);
    }

    @GetMapping(path = "/owner")
    public List<BookingDto> getBookingsByItemOwnerId(@RequestHeader("X-Sharer-User-Id") @Positive Long userId,
                                                     @RequestParam(defaultValue = "ALL") TimeStatus status) {
        return bookingService.getBookingByItemIdAndStatus(userId, status);
    }
}