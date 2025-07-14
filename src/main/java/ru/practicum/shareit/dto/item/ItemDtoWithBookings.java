package ru.practicum.shareit.dto.item;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.dto.booking.BookingDtoSimple;
import ru.practicum.shareit.dto.comment.CommentDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemDtoWithBookings {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
    private BookingDtoSimple nextBooking;
    private BookingDtoSimple lastBooking;
    private List<CommentDto> comments = new ArrayList<>();
}