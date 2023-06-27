package ru.practicum.main.request.service;

import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.model.EventRequest;

public interface RequestService {
    ParticipationRequestDto save(EventRequest eventRequest);
}
