package ru.practicum.main.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByInitiatorId(Integer id, Pageable pageable);

    Event findByIdAndInitiatorId(Integer id, Integer eventId);

    Event findByIdAndStateIs(Integer eventId, State state);

    List<Event> findEventsByInitiatorIdInAndStateInAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
            Integer[] initiatorIds, State[] states, Integer[] categoryIds,
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> searchAllByAnnotationAndCategoryIdInAndStateIsAndEventDateIsAfterAndEventDateIsBefore(
            String query, Integer[] categoryId, State state, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> searchAllByDescriptionAndCategoryIdInAndStateIsAndEventDateIsAfterAndEventDateIsBefore(
            String query, Integer[] categoryId, State state, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Event> searchAllByAnnotationAndCategoryIdInAndStateIsAndEventDateIsAfter(
            String query, Integer[] categoryId, State state, LocalDateTime start, Pageable pageable);

    List<Event> searchAllByDescriptionAndCategoryIdInAndStateIsAndEventDateIsAfter(
            String query, Integer[] categoryId, State state, LocalDateTime start, Pageable pageable);

    List<Event> findAllByCategoryIdInAndStateIsAndEventDateIsAfter(
            Integer[] categoryIds, State state, LocalDateTime start, Pageable pageable);

    List<Event> findAllByCategoryIdInAndEventDateIsAfter(
            Integer[] categoryIds, LocalDateTime start, Pageable pageable);

    List<Event> findAllByStateInAndEventDateIsAfter(
            State[] state, LocalDateTime start, Pageable pageable);

    List<Event> findAllByEventDateIsAfter(LocalDateTime start, Pageable pageable);

    List<Event> findAllByCategoryId(Integer categoryId);

}
