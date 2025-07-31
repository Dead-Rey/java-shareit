package ru.practicum.shareit.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.models.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}