package ru.practicum.shareit.services;


import ru.practicum.shareit.dto.UserCreateDto;
import ru.practicum.shareit.dto.UserUpdateDto;
import ru.practicum.shareit.models.User;

public interface UserService {
    User createUser(UserCreateDto userCreateDto);

    User updateUser(long id, UserUpdateDto userUpdateDto);

    User getUserById(long id);

    void deleteUser(long id);

    void existsByEmail(String email);
}