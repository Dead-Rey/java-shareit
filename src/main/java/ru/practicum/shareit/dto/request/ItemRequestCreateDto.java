package ru.practicum.shareit.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ItemRequestCreateDto {

    @NotNull
    @Size(max = 200)
    private String description;

}