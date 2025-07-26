package ru.practicum.shareit.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.models.ItemRequest;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
}