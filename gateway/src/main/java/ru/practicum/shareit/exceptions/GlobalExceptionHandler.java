package ru.practicum.shareit.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        log.error("Validation error occurred: {}", errors, ex);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errors)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        log.error("Constraint violation error occurred: {}", errors, ex);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(errors)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        boolean validationError = ex instanceof ValidationException
                || ex instanceof IllegalArgumentException
                || ex instanceof MethodArgumentNotValidException;

        boolean authenticationError = ex instanceof AuthenticationException;
        boolean authorizationError = ex instanceof AccessDeniedException;
        boolean notFoundError = ex instanceof NotFoundException;

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        if (validationError) {
            String message = ex instanceof MethodArgumentNotValidException
                    ? extractValidationMessage((MethodArgumentNotValidException) ex)
                    : ex.getMessage();
            log.warn("Validation error: {}", message);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
            response.setMessage(Map.of("error", message != null ? message : "Invalid request parameters"));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (authenticationError) {
            log.warn("Authentication error: {}", ex.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
            response.setMessage(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Authentication required"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else if (authorizationError) {
            log.warn("Authorization error: {}", ex.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
            response.setMessage(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Access denied"));
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        } else if (notFoundError) {
            log.warn("Not found error: {}", ex.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
            response.setMessage(Map.of("error", ex.getMessage() != null ? ex.getMessage() : "Resource not found"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            log.error("Internal server error: {}", ex.getMessage(), ex);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response.setMessage(Map.of("error", "An unexpected error occurred"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractValidationMessage(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestHeaderException(MissingRequestHeaderException ex, HttpServletRequest request) {
        Map<String, String> message = Map.of("error", ex.getMessage());
        log.error("Missing request header error: {}", ex.getMessage(), ex);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        String message = String.format("Parameter '%s' value '%s' could not be converted to required type",
                ex.getName(), ex.getValue());

        log.error("Type mismatch error: {}", message, ex);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(Map.of("error", message))
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpServletRequest request) {

        String message = String.format("Required parameter '%s' is not present", ex.getParameterName());

        log.error("Missing parameter error: {}", message, ex);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(Map.of("error", message))
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}