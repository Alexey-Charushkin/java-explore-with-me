package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.event.dto.UpdateEventUserRequest;
import ru.practicum.main.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto create(Integer userId, Integer catId, Event event);

    List<EventShortDto> findById(Integer userId, Integer from, Integer size);

    EventFullDto findByUserIdAndEventId(Integer userId, Integer eventId);

    EventFullDto  patchByUserIdAndEventId(Integer userId, Integer eventId, Integer catId, String stateAction,
                                                            Event eventUserRequest);

    EventFullDto patchEvent(Integer eventId, Integer catId, String stateAction, Event event);

    List<EventFullDto> findEventsByInitiatorIdsAndStatesAndCategoriesIsAfterStartIsBeforeEnd(
            Integer[] userIds, State[] states, Integer[] categoryIds, LocalDateTime start,
            LocalDateTime end, Integer from, Integer size);
}
