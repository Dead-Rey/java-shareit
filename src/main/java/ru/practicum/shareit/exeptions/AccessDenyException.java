package ru.practicum.shareit.exeptions;

public class AccessDenyException extends RuntimeException {
  public AccessDenyException(String message) {
    super(message);
  }
}