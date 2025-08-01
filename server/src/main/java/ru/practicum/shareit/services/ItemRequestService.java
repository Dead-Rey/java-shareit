package ru.practicum.shareit.services;



import ru.practicum.shareit.dto.ItemRequestCreateDto;
import ru.practicum.shareit.models.ItemRequest;

import java.util.List;

public interface ItemRequestService {
    ItemRequest createItemRequest(long userId, ItemRequestCreateDto requestDto);

    List<ItemRequest> getUserRequests(long userId);

    List<ItemRequest> getAllRequests(long userId);

    ItemRequest getRequestById(long userId, long requestId);
}