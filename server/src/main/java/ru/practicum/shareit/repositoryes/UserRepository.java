package ru.practicum.shareit.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.models.User;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}