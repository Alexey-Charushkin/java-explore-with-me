package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.model.Event;

import java.util.List;

public interface EventService {
     EventFullDto create(Integer userId, Integer catId, Event event);
     List<EventShortDto> findById(Integer userId, Integer from, Integer size);
}
