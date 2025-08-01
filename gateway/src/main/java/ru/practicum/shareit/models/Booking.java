package ru.practicum.shareit.models;

import lombok.*;
import ru.practicum.shareit.enums.BookingStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"item"})
public class Booking {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User user;
    private BookingStatus status;
}