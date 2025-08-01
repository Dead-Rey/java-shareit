package ru.practicum.shareit.repositoryes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.dto.booking.BookingDtoSimple;
import ru.practicum.shareit.enums.Status;
import ru.practicum.shareit.models.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerId(Long bookerId);

    List<Booking> findByBookerIdAndEndBeforeOrderByEndAsc(Long bookerId, LocalDateTime end);

    List<Booking> findByBookerIdAndStartAfterOrderByStartAsc(Long bookerId, LocalDateTime start);

    List<Booking> findByBookerIdAndEndAfterAndStartBeforeOrderByStartAsc(Long bookerId, LocalDateTime end, LocalDateTime start);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :bookerId AND b.end > :now AND b.start < :now ORDER BY b.start ASC")
    List<Booking> findCurrentBookingsByBooker(@Param("bookerId") Long bookerId, @Param("now") LocalDateTime now);

    List<Booking> findByBookerIdAndStatusOrderByStartAsc(Long bookerId, Status status);

    List<Booking> findByItemOwnerIdAndStatusOrderByStartAsc(Long bookerId, Status status);

    List<Booking> findByItemOwnerIdOrderByStartAsc(Long itemOwnerId);

    List<Booking> findByItemOwnerIdAndEndBeforeOrderByEndAsc(Long itemOwnerId, LocalDateTime end);

    List<Booking> findByItemOwnerIdAndStartAfterOrderByStartAsc(Long itemOwnerId, LocalDateTime start);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = :ownerId AND b.end > :now AND b.start < :now")
    List<Booking> findCurrentBookingsByItemOwnerId(@Param("ownerId") Long bookerId, @Param("now") LocalDateTime now);

    @Query("SELECT new ru.practicum.shareit.dto.booking.BookingDtoSimple(b.id, b.start, b.end) FROM Booking b WHERE b.item.id = :itemId AND b.start > CURRENT_TIMESTAMP ORDER BY b.start ASC")
    BookingDtoSimple findFirstByItemIdAndStartAfterNow(Long itemId);

    @Query("SELECT new ru.practicum.shareit.dto.booking.BookingDtoSimple(b.id, b.start, b.end) FROM Booking b WHERE b.item.id = :itemId AND b.end < CURRENT_TIMESTAMP ORDER BY b.end DESC")
    BookingDtoSimple findFirstByItemIdAndEndBeforeNow(Long itemId);

    Optional<Booking> findFirstByBookerIdAndItemIdOrderByStartAsc(Long bookerId, Long itemId);

}