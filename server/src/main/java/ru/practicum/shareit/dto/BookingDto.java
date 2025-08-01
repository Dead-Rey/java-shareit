package ru.practicum.shareit.dto;

import lombok.*;
import ru.practicum.shareit.enums.BookingStatus;
import ru.practicum.shareit.models.Item;
import ru.practicum.shareit.models.User;


import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private BookingStatus status;
}