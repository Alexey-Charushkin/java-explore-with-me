package ru.practicum.main.event.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.service.CategoryService;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.locations.dao.LocationsRepository;
import ru.practicum.main.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        event.setState(State.PENDING);
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
        return EventMapper.toEventShortDtoList(eventRepository.findByInitiatorId(userId, page));
    }

    @Override
    public EventFullDto findByUserIdAndEventId(Integer userId, Integer eventId) {
        Optional<Event> event = Optional.of(eventRepository.findByIdAndInitiatorId(userId, eventId));
        if (!event.isPresent()) {
            throw new NotFoundException("Event with userId " + userId + " and eventId " + eventId + " not found");
        }
        return EventMapper.eventToEventFullDto(event.get());
    }

    @Override
    public EventFullDto patchByUserIdAndEventId(Integer userId, Integer eventId, Integer catId, String stateAction,
                                                Event event) {
        Optional<Event> optionalEvent = Optional.of(eventRepository.findByIdAndInitiatorId(userId, eventId));
        Event oldEvent;

        if (!optionalEvent.isPresent()) {
            throw new NotFoundException("Event with userId " + userId + " and eventId " + eventId + " not found");
        }
        if (!optionalEvent.get().getInitiator().getId().equals(userId)) {
            throw new BadRequestException("User is not initiator");
        }
        if (optionalEvent.get().getState().equals(State.CANCELED)
                || optionalEvent.get().getState().equals(State.PENDING)) {
            oldEvent = optionalEvent.get();
            if (oldEvent.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
                updateFields(stateAction, catId, oldEvent, event);
            }

        } else {
            throw new ConflictException("Conflict");
        }
        eventRepository.save(oldEvent);
        return EventMapper.eventToEventFullDto(oldEvent);
    }

    @Override
    public EventFullDto patchEvent(Integer eventId, Integer catId, String stateAction,
                                   Event event) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        Event oldEvent;

        if (!optionalEvent.isPresent()) {
            throw new NotFoundException("Event with Id " + eventId + " not found");
        }

        if (optionalEvent.get().getState().equals(State.CANCELED) || optionalEvent.get().getState().equals(State.PENDING)) {

            oldEvent = optionalEvent.get();
            if (oldEvent.getEventDate().isAfter(LocalDateTime.now().plusHours(1))) {
                updateFields(stateAction, catId, oldEvent, event);
            }

        } else {
            throw new ConflictException("Conflict");
        }
        eventRepository.save(oldEvent);
        return EventMapper.eventToEventFullDto(oldEvent);
    }

    @Override
    public List<EventFullDto> findEventsByInitiatorIdsAndStatesAndCategoriesIsAfterStartIsBeforeEnd(
            Integer[] userIds, State[] states, Integer[] categoryIds, LocalDateTime start, LocalDateTime end,
            Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size);
        List<Event> events = eventRepository
                .findEventsByInitiatorIdInAndStateInAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
                        userIds, states, categoryIds, start, end, page);
        return EventMapper.toEventFullDtoList(events);
    }

    private void updateFields(String stateAction, Integer catId, Event oldEvent, Event event) {
        Category newCategory;

        if (event.getAnnotation() != null
                && !event.getAnnotation().equals(oldEvent.getAnnotation())) {
            oldEvent.setAnnotation(event.getAnnotation());
        }

        if (catId != null) {
            newCategory = categoryService.findById(catId);
            if (!newCategory.equals(oldEvent.getCategory())) {
                oldEvent.setCategory(newCategory);
            }
        }

        if (event.getDescription() != null && !event.getDescription().equals(oldEvent.getDescription())) {
            oldEvent.setDescription(event.getDescription());
        }

        if (event.getEventDate() != null && event.getEventDate().isAfter(oldEvent.getEventDate())) {
            if (event.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
                oldEvent.setEventDate(event.getEventDate());
            }
        }

        if (event.getLocation() != null && event.getLocation().getId() != null &&
                !event.getLocation().equals(oldEvent.getLocation())) {

            oldEvent.setLocation(event.getLocation());
        }

//                if ((Boolean) event.isPaid() != null) {
//                    oldEvent.get().setPaid(event.isPaid());
//                    if(event.isPaid() && !oldEvent.get().isPaid()) oldEvent.get().setPaid(event.isPaid());
//                    if(!event.isPaid() && oldEvent.get().isPaid()) oldEvent.get().setPaid(event.isPaid());
//                }

        if (event.getParticipantLimit() != null && !event.getParticipantLimit().equals(oldEvent.getParticipantLimit())) {
            oldEvent.setParticipantLimit(event.getParticipantLimit());
        }

        if ((Boolean) event.isRequestModeration() != null && !event.isRequestModeration() == oldEvent.isRequestModeration()) {
            oldEvent.setRequestModeration(event.isRequestModeration());
        }

        if (event.getParticipantLimit() != null && !event.getParticipantLimit().equals(oldEvent.getParticipantLimit())) {
            oldEvent.setParticipantLimit(event.getParticipantLimit());
        }

        if (event.getTitle() != null && !event.getTitle().equals(oldEvent.getTitle())) {
            oldEvent.setTitle(event.getTitle());
        }

        if (stateAction != null) {
            if (stateAction.equals("CANCEL_REVIEW")) oldEvent.setState(State.CANCELED);
            if (stateAction.equals("PUBLISH_EVENT")) oldEvent.setState(State.PUBLISHED);
        }
    }
}
