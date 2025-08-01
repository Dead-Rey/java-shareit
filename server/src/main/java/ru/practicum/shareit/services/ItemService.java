package ru.practicum.shareit.services;


import ru.practicum.shareit.dto.CommentCreateDto;
import ru.practicum.shareit.dto.ItemCreateDto;
import ru.practicum.shareit.dto.ItemDto;
import ru.practicum.shareit.models.Comment;
import ru.practicum.shareit.models.Item;

import java.util.List;

public interface ItemService {
    Item createItem(long userId, ItemCreateDto itemCreateDto);

    Item updateItem(long userId, long itemId, ItemDto itemDto);

    Item getItemById(long itemId);

    List<Item> getItemsByOwner(long userId);

    List<Item> searchItems(String text);

    Comment createComment(long userId, long itemId, CommentCreateDto commentCreateDto);
}