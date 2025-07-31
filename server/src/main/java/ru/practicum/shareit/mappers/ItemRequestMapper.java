package ru.practicum.shareit.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.dto.ItemRequestCreateDto;
import ru.practicum.shareit.dto.ItemRequestDto;
import ru.practicum.shareit.models.ItemRequest;


@Mapper
public interface ItemRequestMapper {
    ItemRequestMapper INSTANCE = Mappers.getMapper(ItemRequestMapper.class);

    ItemRequest toItemRequest(ItemRequestCreateDto itemRequestCreateDto);

    @Mapping(target = "created", source = "created")
    @Mapping(target = "requestor", source = "user")
    @Mapping(target = "items", source = "items")
    ItemRequestDto toItemRequestDto(ItemRequest itemRequest);
}