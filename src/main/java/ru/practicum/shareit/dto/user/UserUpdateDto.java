package ru.practicum.shareit.dto.user;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateDto {

    @Nullable
    private String name;

    @Nullable
    @Email(message = "Неверный формат email")
    private String email;
}