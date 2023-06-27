package ru.practicum.main.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.model.EventRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//    public EventRequest toEventRequest (ParticipationRequestDto participationRequestDto) {
//        return new EventRequest(
//                LocalDateTime.parse(participationRequestDto.getCreated(), formatter),
//                participationRequestDto.getEvent(),
//                participationRequestDto.getId(),
//                participationRequestDto.getRequester(),
//                participationRequestDto.getStatus()
//        );
//    }

    public ParticipationRequestDto toParticipationRequestDto (EventRequest eventRequest) {
        return new ParticipationRequestDto(
                eventRequest.getCreated().format(formatter),
                eventRequest.getEvent(),
                eventRequest.getId(),
                eventRequest.getRequester(),
                eventRequest.getStatus().toString()
        );
    }

    public List<ParticipationRequestDto > toParticipationRequestDtoList(List<EventRequest> eventRequests) {
        return eventRequests.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

}
