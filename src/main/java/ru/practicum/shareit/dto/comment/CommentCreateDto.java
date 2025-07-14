package ru.practicum.shareit.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateDto {
    @NotNull
    @NotEmpty
    private String text;
}