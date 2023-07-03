package ru.practicum.main.event.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.model.Event;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto create(Integer userId, Integer catId, Event event);

    List<EventShortDto> findById(Integer userId, Integer from, Integer size);

    EventFullDto findByUserIdAndEventId(Integer userId, Integer eventId);

    EventFullDto patchByUserIdAndEventId(Integer userId, Integer eventId, String stateAction,
                                         UpdateEvent updateEvent);

    EventFullDto patchEvent(Integer eventId, String stateAction, UpdateEvent updateEvent);

    List<EventFullDto> findEventsByInitiatorIdsAndStatesAndCategoriesIsAfterStartIsBeforeEnd(
            Integer[] userIds, State[] states, Integer[] categoryIds, String start,
            String end, Integer from, Integer size);

    EventFullDto findById(Integer eventId);

    List<EventShortDto> searchEvents(String query, Integer[] categoryIds, boolean pais, String start,
                                     String end, boolean onlyAvailable, String sort, Integer from, Integer size);
}
