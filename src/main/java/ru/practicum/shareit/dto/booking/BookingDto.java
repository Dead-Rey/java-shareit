package ru.practicum.shareit.dto.booking;

import lombok.Data;
import ru.practicum.shareit.dto.item.ItemDtoSimple;
import ru.practicum.shareit.dto.user.UserIdDto;
import ru.practicum.shareit.enums.Status;

import java.time.LocalDateTime;

@Data
public class BookingDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private UserIdDto booker;
    private ItemDtoSimple item;
    private Status status;
}