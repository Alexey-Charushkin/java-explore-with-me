package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.event.model.Event;

@UtilityClass
public class EventMapper {

    //    public Event newEventDtoToEvent(User user, Location location, NewEventDto eventNewDto, Category category) {
//        return new Event(
//                eventNewDto.getId(),
//                eventNewDto.getTitle(),
//                eventNewDto.getDescription(),
//                eventNewDto.getAnnotation(),
//                EventState.valueOf("PENDING"),
//                category,
//                LocalDateTime.now(),
//                LocalDateTime.parse(eventNewDto.getEventDate().replaceAll(" ", "T")),
//                null,
//                0,
//                location,
//                user,
//                eventNewDto.isPaid(),
//                eventNewDto.getParticipantLimit() == null ? 0 : eventNewDto.getParticipantLimit(),
//                eventNewDto.getRequestModeration() == null || eventNewDto.getRequestModeration()
//                );
//    }

    public Event newEventDtoToEvent(NewEventDto newEventDto) {
        return new Event(
                newEventDto.getAnnotation(),
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                newEventDto.getLocation(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit() == null ? 0 : newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(),
                State.valueOf("PENDING"),
                newEventDto.getTitle()
        );
    }

    public EventFullDto eventToEventFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                event.getCategory(),
                event.getConfirmedRequests(),
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                event.getId(),
                event.getInitiator(),
                event.getLocation(),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                event.getViews()
        );
    }

    public EventShortDto eventToEventShortDto(Event event) {
        return new EventShortDto(
                event.getAnnotation(),
                event.getCategory(),
                event.getConfirmedRequests(),
                event.getEventDate(),
                event.getId(),
                event.getInitiator(),
                event.isPaid(),
                event.getTitle(),
                event.getViews()
        );
    }
}





