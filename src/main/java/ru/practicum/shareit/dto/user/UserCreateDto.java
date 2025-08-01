package ru.practicum.shareit.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreateDto {


    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
}