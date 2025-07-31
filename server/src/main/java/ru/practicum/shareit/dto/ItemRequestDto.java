package ru.practicum.shareit.dto;

import lombok.*;
import ru.practicum.shareit.models.Item;
import ru.practicum.shareit.models.User;


import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Long id;
    private String description;
    private User requestor;
    private LocalDateTime created;
    private List<Item> items;
}