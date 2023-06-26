package ru.practicum.main.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            String query, Integer[] categoryId, State state, LocalDateTime start,  Pageable pageable);
    List<Event> searchAllByDescriptionAndCategoryIdInAndStateIsAndEventDateIsAfter(
            String query, Integer[] categoryId, State state, LocalDateTime start, Pageable pageable);



//    String query, Integer[] categoryIds, boolean pais, LocalDateTime start,
//    LocalDateTime end, boolean onlyAvailable, String sort, Integer from,
//    Integer size

    //      это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
//        текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв

//        если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, которые произойдут позже текущей даты и времени
//        информация о каждом событии должна включать в себя количество просмотров и количество уже одобренных заявок на участие
//        информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, нужно сохранить в сервисе статистики
}
