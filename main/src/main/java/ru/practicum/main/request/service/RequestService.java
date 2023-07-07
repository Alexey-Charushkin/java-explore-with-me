package ru.practicum.main.request.service;

import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    ParticipationRequestDto save(Integer userId, Integer eventId);

    List<ParticipationRequestDto> findById(Integer userId);

    ParticipationRequestDto cancelEventRequest(Integer userId, Integer requestId);

    List<ParticipationRequestDto> findRequestsByUserIdAndEventId(Integer userId, Integer requestId);

    EventRequestStatusUpdateResult patchRequestsByUserIdAndEventId(Integer userId, Integer eventId,
                                                                   EventRequestStatusUpdateRequest updateRequest);
}
