package ru.practicum.shareit.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestCreateDto {
    private String description;
}