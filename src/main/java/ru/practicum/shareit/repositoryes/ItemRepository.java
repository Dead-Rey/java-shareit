package ru.practicum.shareit.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.models.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.bookings WHERE i.owner.id = :ownerId")
    List<Item> findAllByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT i FROM Item i WHERE (LOWER(i.name) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(i.description) LIKE LOWER(CONCAT('%', :text, '%'))) AND i.available = true")
    List<Item> findByText(@Param("text") String text);

    Optional<Item> findById(Long id);
}