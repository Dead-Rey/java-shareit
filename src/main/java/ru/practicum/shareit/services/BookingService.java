package ru.practicum.shareit.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.dto.booking.BookingCreateDto;
import ru.practicum.shareit.dto.booking.BookingDto;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.enums.TimeStatus;
import ru.practicum.shareit.exeptions.AccessDenyException;
import ru.practicum.shareit.exeptions.NotFoundException;
import ru.practicum.shareit.mappers.BookingMapper;
import ru.practicum.shareit.models.Booking;
import ru.practicum.shareit.models.User;
import ru.practicum.shareit.repositoryes.BookingRepository;
import ru.practicum.shareit.repositoryes.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final UserRepository userRepository;

    @Transactional
    public BookingDto createBooking(BookingCreateDto createDto, Long userId) {
        Booking booking = bookingMapper.fromDto(createDto);
        boolean existsUser = userRepository.existsById(userId);
        if (!existsUser) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (booking.getEnd().equals(booking.getStart())) {
            throw new IllegalStateException("Срок аренды вещи не может быть нулевым");
        }
        if (booking.getItem() == null) {
            throw new NotFoundException("Некорректные данные");
        }
        boolean available = booking.getItem().getAvailable();
        if (!available) {
            throw new IllegalStateException("Вещь не доступна для бронирования");
        }
        booking.setBooker(findUserById(userId));
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    @Transactional
    public BookingDto approveBooking(Long id, Long userId, boolean approved) {
        Booking booking = findBooking(id);
        checkBooking(booking, userId);
        if (approved) {
            booking.setStatus(Status.APPROVED);
        } else {
            booking.setStatus(Status.REJECTED);
        }
        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    public BookingDto getBooking(Long id) {
        return bookingMapper.toDto(findBooking(id));
    }

    public List<BookingDto> getBookingByBookerIdAndStatus(Long bookerId, TimeStatus status) {
        checkUser(bookerId);
        List<Booking> bookings;
        LocalDateTime now = LocalDateTime.now();
        bookings = switch (status) {
            case REJECTED -> bookingRepository.findByBookerIdAndStatusOrderByStartAsc(bookerId, Status.REJECTED);
            case ALL -> bookingRepository.findAllByBookerId(bookerId);
            case PAST -> bookingRepository.findByBookerIdAndEndBeforeOrderByEndAsc(bookerId, now);
            case FUTURE -> bookingRepository.findByBookerIdAndStartAfterOrderByStartAsc(bookerId, now);
            case CURRENT -> bookingRepository.findCurrentBookingsByBooker(bookerId, now);
            case WAITING -> bookingRepository.findByBookerIdAndStatusOrderByStartAsc(bookerId, Status.WAITING);
        };
        return bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    public List<BookingDto> getBookingByItemIdAndStatus(Long itemId, TimeStatus status) {
        checkUser(itemId);
        List<Booking> bookings;
        LocalDateTime now = LocalDateTime.now();
        bookings = switch (status) {
            case REJECTED -> bookingRepository.findByItemOwnerIdAndStatusOrderByStartAsc(itemId, Status.REJECTED);
            case ALL -> bookingRepository.findByItemOwnerIdOrderByStartAsc(itemId);
            case PAST -> bookingRepository.findByItemOwnerIdAndEndBeforeOrderByEndAsc(itemId, now);
            case FUTURE -> bookingRepository.findByItemOwnerIdAndStartAfterOrderByStartAsc(itemId, now);
            case CURRENT -> bookingRepository.findCurrentBookingsByItemOwnerId(itemId, now);
            case WAITING -> bookingRepository.findByItemOwnerIdAndStatusOrderByStartAsc(itemId, Status.WAITING);
        };
        return bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
    }

    private Booking findBooking(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Бронирование с id '%s' не найдено", id)));
    }

    private void checkBooking(Booking booking, Long userId) {
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new AccessDenyException("Подтвердить бронирование может только владелец вещи");
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    private void checkUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new NotFoundException("Пользователь не найден");
        }
    }
}