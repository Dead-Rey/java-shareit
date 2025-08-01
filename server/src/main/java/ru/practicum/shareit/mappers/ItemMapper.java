package ru.practicum.shareit.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.dto.CommentDto;
import ru.practicum.shareit.dto.CommentForItemDto;
import ru.practicum.shareit.dto.ItemCreateDto;
import ru.practicum.shareit.dto.ItemDto;
import ru.practicum.shareit.models.Comment;
import ru.practicum.shareit.models.Item;
import ru.practicum.shareit.models.ItemRequest;


import java.util.List;

@Mapper
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "request", source = "requestId", qualifiedByName = "idToItemRequest")
    Item toItem(ItemCreateDto itemCreateDto);

    @Named("idToItemRequest")
    default ItemRequest mapIdToItemRequest(Long requestId) {
        if (requestId == null) {
            return null;
        }
        ItemRequest request = new ItemRequest();
        request.setId(requestId);
        return request;
    }

    @Mapping(target = "itemRequest", source = "request")
    ItemDto toItemDto(Item item);

    @Mapping(target = "item", ignore = true)
    @Mapping(target = "created", source = "createdAt")
    @Mapping(target = "authorName", source = "user.name")
    CommentDto toCommentDto(Comment comment);

    @Mapping(target = "itemId", source = "item.id")
    @Mapping(target = "created", source = "createdAt")
    @Mapping(target = "authorName", source = "user.name")
    CommentForItemDto toCommentForItemDto(Comment comment);

    default List<CommentForItemDto> mapComments(List<Comment> comments) {
        if (comments == null) {
            return null;
        }
        return comments.stream()
                .map(this::toCommentForItemDto)
                .toList();
    }
}