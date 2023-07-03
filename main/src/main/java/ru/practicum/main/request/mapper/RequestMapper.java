package ru.practicum.main.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.model.EventRequest;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ParticipationRequestDto toParticipationRequestDto(EventRequest eventRequest) {
        return new ParticipationRequestDto(
                eventRequest.getCreated().format(formatter),
                eventRequest.getEvent().getId(),
                eventRequest.getId(),
                eventRequest.getRequester().getId(),
                eventRequest.getStatus().toString()
        );
    }

    public List<ParticipationRequestDto> toParticipationRequestDtoList(List<EventRequest> eventRequests) {
        return eventRequests.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

}