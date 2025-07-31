package ru.practicum.shareit.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.dto.BookingCreateDto;


public class StartBeforeEndValidator implements ConstraintValidator<ValidBookingDates, BookingCreateDto> {
    @Override
    public boolean isValid(BookingCreateDto bookingCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        if (bookingCreateDto.getStart() == null || bookingCreateDto.getEnd() == null) {
            return true;
        }
        return bookingCreateDto.getStart().isBefore(bookingCreateDto.getEnd());
    }
}