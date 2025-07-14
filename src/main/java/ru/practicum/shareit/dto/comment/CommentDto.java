package ru.practicum.shareit.dto.comment;

import lombok.Data;

import java.time.Instant;

@Data
public class CommentDto {
    private Long id;
    private String text;
    private String authorName;
    private Instant created;
}