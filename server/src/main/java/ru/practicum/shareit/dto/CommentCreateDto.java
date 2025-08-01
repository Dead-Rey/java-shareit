package ru.practicum.shareit.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDto {
    private String text;
}