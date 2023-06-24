package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.dto.mapper.CategoryMapper;
import ru.practicum.main.user.mapper.UserMapper;

import java.time.format.DateTimeFormatter;

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
//    }(в формате "yyyy-MM-dd HH:mm:ss")

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event newEventDtoToEvent(NewEventDto newEventDto) {
        return new Event(
                newEventDto.getAnnotation(),
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                newEventDto.getLocation(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit() == null ? 0 : newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(),
                newEventDto.getTitle()
        );
    }

    public EventFullDto eventToEventFullDto(Event event) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.categoryToCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getCreatedOn().toString(),
                event.getDescription(),
                event.getEventDate().toString(),
                event.getId(),
                UserMapper.userToUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn().toString(),
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
                event.getEventDate().toString(),
                event.getId(),
                UserMapper.userToUserShortDto(event.getInitiator()),
                event.isPaid(),
                event.getTitle(),
                event.getViews()
        );
    }
}





