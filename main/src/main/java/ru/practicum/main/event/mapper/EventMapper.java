package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class EventMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event newEventDtoToEvent(NewEventDto newEventDto) {
        return new Event(
                newEventDto.getAnnotation(),
                newEventDto.getDescription(),
                LocalDateTime.parse(newEventDto.getEventDate(), formatter),
                newEventDto.getLocation(),
                Boolean.parseBoolean(newEventDto.getPaid()),
                newEventDto.getParticipantLimit() == null ? 0 : newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration() == null ? "true" : newEventDto.getRequestModeration(),
                newEventDto.getTitle()
        );
    }

    public EventFullDto eventToEventFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.categoryToCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn().format(formatter),
                event.getDescription(),
                event.getEventDate().format(formatter),
                event.getId(),
                UserMapper.userToUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn().format(formatter),
                event.isRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                event.getViews()
        );
    }

    public EventShortDto eventToEventShortDto(Event event) {
        return new EventShortDto(
                event.getAnnotation(),
                CategoryMapper.categoryToCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getEventDate().format(formatter),
                event.getId(),
                UserMapper.userToUserShortDto(event.getInitiator()),
                event.isPaid(),
                event.getTitle(),
                event.getViews()
        );
    }

    public List<EventShortDto> toEventShortDtoList(List<Event> eventList) {
        return eventList.stream()
                .map(EventMapper::eventToEventShortDto)
                .collect(Collectors.toList());
    }

    public List<EventFullDto> toEventFullDtoList(List<Event> eventList) {
        return eventList.stream()
                .map(EventMapper::eventToEventFullDto)
                .collect(Collectors.toList());
    }
}





