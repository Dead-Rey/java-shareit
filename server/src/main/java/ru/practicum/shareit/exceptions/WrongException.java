package ru.practicum.shareit.exceptions;

public class WrongException extends RuntimeException {
    public WrongException(String message) {
        super(message);
    }
}