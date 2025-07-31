package ru.practicum.shareit.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.dto.UserCreateDto;
import ru.practicum.shareit.dto.UserDto;
import ru.practicum.shareit.models.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserCreateDto userCreateDto);

    UserDto toUserDto(User user);
}