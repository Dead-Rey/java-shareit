package ru.practicum.shareit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ItemCreateDto {
    private String name;
    private String description;
    private Boolean available;
    private Long requestId;
}