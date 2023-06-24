package ru.practicum.main.event.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.service.CategoryService;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.EventState;
import ru.practicum.main.locations.dao.LocationsRepository;
import ru.practicum.main.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Log4j2
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventRepository eventRepository;
    private final LocationsRepository locationsRepository;

    @Override
    public EventFullDto create(Integer userId, Integer catId, Event event) {
        event.setCreatedOn(LocalDateTime.now());
        event.setInitiator(userService.findById(userId));
        event.setCategory(categoryService.findById(catId));
        event.setState(EventState.PENDING);
        event.setConfirmedRequests(0);
        event.setPublishedOn(LocalDateTime.now());
        event.setViews(0);
        locationsRepository.save(event.getLocation());
        eventRepository.save(event);
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public List<EventShortDto> findById(Integer userId, Integer from, Integer size) {

        Pageable page = PageRequest.of(from, size);
        return EventMapper.toEventDtoList(eventRepository.findByInitiatorId(userId, page));
    }
}
