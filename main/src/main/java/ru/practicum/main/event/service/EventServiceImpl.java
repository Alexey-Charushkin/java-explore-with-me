package ru.practicum.main.event.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.service.CategoryService;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.service.UserService;

@Getter
@Setter
@Log4j2
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserService userService;
    private final CategoryService categoryService;

    private final EventRepository eventRepository;

    @Override
    public EventFullDto create(Integer userId, Integer catId, Event event) {
        event.setInitiator(userService.findById(userId));
        event.setCategory(categoryService.findById(catId));
        eventRepository.save(event);
        return EventMapper.eventToEventFullDto(event);
    }
}
