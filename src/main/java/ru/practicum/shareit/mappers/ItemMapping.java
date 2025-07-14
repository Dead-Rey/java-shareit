package ru.practicum.shareit.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.dto.comment.CommentDto;
import ru.practicum.shareit.dto.item.*;
import ru.practicum.shareit.models.Comment;
import ru.practicum.shareit.models.Item;

@Mapper(componentModel = "spring", uses = BookingMapper.class)
public interface ItemMapping {

    Item fromDto(ItemCreateDto item);

    Item fromUpdateDto(ItemUpdateDto item);

    ItemDto toDto(Item item);

    ItemDtoSimple toDtoSimple(Item item);

    @Mapping(target = "nextBooking", source = "nextBooking")
    @Mapping(target = "lastBooking", source = "lastBooking")
    ItemDtoWithBookings toDtoWithBookings(Item item);

    @Mapping(target = "authorName", expression = "java(comment.getAuthor().getName())")
    CommentDto toDtoComment(Comment comment);

}