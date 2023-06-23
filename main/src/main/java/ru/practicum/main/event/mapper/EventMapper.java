package ru.practicum.main.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.event.dto.EventNewDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.Location;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {

//    public Event newEventDtoToEvent(User user, Location location, EventNewDto eventNewDto, Category category) {
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
}

