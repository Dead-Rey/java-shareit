package ru.practicum.shareit.mappers;

import org.mapstruct.Mapper;
import ru.practicum.shareit.dto.request.ItemRequestCreateDto;
import ru.practicum.shareit.dto.request.ItemRequestDto;
import ru.practicum.shareit.models.ItemRequest;

@Mapper(componentModel = "spring")
public interface ItemRequestMapping {

    ItemRequest fromDto(ItemRequestCreateDto itemRequestCreateDto);

    ItemRequestDto toDto(ItemRequest itemRequest);
}