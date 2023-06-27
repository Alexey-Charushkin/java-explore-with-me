package ru.practicum.main.request.service;

import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.model.EventRequest;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto save(Integer userId, Integer eventId);

    List <ParticipationRequestDto> findById(Integer userId);

    ParticipationRequestDto cancelEventRequest(Integer userId, Integer requestId);
}
