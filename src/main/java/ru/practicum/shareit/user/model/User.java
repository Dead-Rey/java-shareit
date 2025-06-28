package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;
    @Email(message = "Почта должна иметь определенный вид")
    @NotBlank(message = "Почта не должна быть пустой")
    private String email;
}