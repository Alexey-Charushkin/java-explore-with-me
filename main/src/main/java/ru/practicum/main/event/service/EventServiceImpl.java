package ru.practicum.main.event.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.service.CategoryService;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.locations.dao.LocationsRepository;
import ru.practicum.main.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        boolean check = checkEvent(optionalEvent);
        Event eventToSave = optionalEvent.get();
        if (!optionalEvent.get().getInitiator().getId().equals(userId)) {
            throw new BadRequestException("User is not initiator");
        }
        if (check) {
            updateFields(stateAction, catId, eventToSave, event);
            eventRepository.save(eventToSave);
        }
        return EventMapper.eventToEventFullDto(eventToSave);
    }

    @Override
    public EventFullDto patchEvent(Integer eventId, Integer catId, String stateAction,
                                   Event event) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        boolean check = checkEvent(optionalEvent);
        Event eventToSave = optionalEvent.get();
        if (check) {
            updateFields(stateAction, catId, eventToSave, event);
            eventRepository.save(eventToSave);
        }
        return EventMapper.eventToEventFullDto(eventToSave);
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

    @Override
    public EventFullDto findById(Integer eventId) {
        Event event = eventRepository.findByIdAndStateIs(eventId, State.PUBLISHED);
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public List<EventShortDto> searchEvents(String query, Integer[] categoryIds, boolean pais, LocalDateTime start,
                                            LocalDateTime end, boolean onlyAvailable, String sort, Integer from,
                                            Integer size) {
        Pageable pageable = PageRequest.of(from, size);//.withSort(Sort.by(sort).ascending());
        List<Event> eventList;
        List<Event> sortList = new ArrayList<>();

        if (start == null && end == null) {
            eventList = eventRepository.searchAllByAnnotationAndCategoryIdInAndStateIsAndEventDateIsAfter
                    (query, categoryIds, State.PUBLISHED, LocalDateTime.now(), pageable);
            if (eventList.size() == 0) {
                eventList = eventRepository
                        .searchAllByDescriptionAndCategoryIdInAndStateIsAndEventDateIsAfter
                                (query, categoryIds, State.PUBLISHED, LocalDateTime.now(), pageable);
            }
        } else {
            eventList = eventRepository
                    .searchAllByAnnotationAndCategoryIdInAndStateIsAndEventDateIsAfterAndEventDateIsBefore
                            (query, categoryIds, State.PUBLISHED, start, end, pageable);
            if (eventList.size() == 0) {
                eventList = eventRepository
                        .searchAllByDescriptionAndCategoryIdInAndStateIsAndEventDateIsAfterAndEventDateIsBefore
                                (query, categoryIds, State.PUBLISHED, start, end, pageable);
            }
        }



        if ((Boolean) pais != null) {




            if (pais) {
                for (Event event : eventList) {
                    if (event.isPaid()) {
                        sortList.add(event);
                    }
                }
                eventList.clear();
                eventList.addAll(sortList);
            } else {
                for (Event event : eventList) {
                    if (!event.isPaid()) {
                        sortList.add(event);
                    }
                }
                eventList.clear();
                eventList.addAll(sortList);
            }
        }

        if ((Boolean) onlyAvailable != null) {
            if (onlyAvailable) {
                for (Event event : eventList) {
                    if (event.getConfirmedRequests() < event.getParticipantLimit()) {
                        sortList.add(event);
                    }
                }
                eventList.clear();
                eventList.addAll(sortList);
            }
        }

        return EventMapper.toEventShortDtoList(eventList);
    }

    private boolean checkEvent(Optional<Event> optionalEvent) {
        Event oldEvent;
        boolean check = false;
        if (!optionalEvent.isPresent()) {
            throw new NotFoundException("Event with userId not found");
        }
        if (optionalEvent.get().getState().equals(State.CANCELED)
                || optionalEvent.get().getState().equals(State.PENDING)) {
            oldEvent = optionalEvent.get();
            if (oldEvent.getEventDate().isAfter(LocalDateTime.now().plusHours(1))) {
                check = true;
            }
        }
        return check;
    }

    private void updateFields(String stateAction, Integer catId, Event oldEvent, Event event) {
        Category newCategory;

        if (stateAction != null) {
            if (stateAction.equals("CANCEL_REVIEW")) oldEvent.setState(State.CANCELED);
            if (stateAction.equals("PUBLISH_EVENT")) oldEvent.setState(State.PUBLISHED);
        }

        if (!oldEvent.getState().equals(State.CANCELED)) {

            if (event.getAnnotation() != null) oldEvent.setAnnotation(event.getAnnotation());

            if (catId != null) {
                newCategory = categoryService.findById(catId);
                oldEvent.setCategory(newCategory);
            }

            if (event.getDescription() != null) oldEvent.setDescription(event.getDescription());

            if (event.getEventDate() != null && event.getEventDate().isAfter(oldEvent.getEventDate())) {
                if (event.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
                    oldEvent.setEventDate(event.getEventDate());
                }
            }

            if (event.getLocation() != null && event.getLocation().getId() != null) {
                oldEvent.setLocation(event.getLocation());
            }

            if ((Boolean) event.isPaid() != null) oldEvent.setPaid(event.isPaid());

            if (event.getParticipantLimit() != null) oldEvent.setParticipantLimit(event.getParticipantLimit());

            if ((Boolean) event.isRequestModeration() != null)
                oldEvent.setRequestModeration(event.isRequestModeration());

            if (event.getParticipantLimit() != null) oldEvent.setParticipantLimit(event.getParticipantLimit());

            if (event.getTitle() != null) oldEvent.setTitle(event.getTitle());
        }
    }
}
