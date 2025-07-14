package ru.practicum.shareit.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}