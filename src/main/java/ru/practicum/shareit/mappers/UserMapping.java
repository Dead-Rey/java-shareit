package ru.practicum.shareit.mappers;

import org.mapstruct.Mapper;
import ru.practicum.shareit.dto.user.UserCreateDto;
import ru.practicum.shareit.dto.user.UserDto;
import ru.practicum.shareit.dto.user.UserIdDto;
import ru.practicum.shareit.dto.user.UserUpdateDto;
import ru.practicum.shareit.models.User;

@Mapper(componentModel = "spring")
public interface UserMapping {

    User fromDto(UserCreateDto userDto);

    User fromUpdateDto(UserUpdateDto userUpdateDto);

    UserDto toDto(User user);

    UserIdDto toUserIdDto(User user);
}