package ru.practicum.main.event.service;


import dto.StatsDtoToGetStats;
import dto.StatsDtoToReturn;
import dto.StatsDtoToSave;
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
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.locations.dao.LocationsRepository;
import ru.practicum.main.request.dao.RequestRepository;
import ru.practicum.main.user.service.UserService;
import ru.practucum.client.StatisticsWebClient;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    private final RequestRepository requestRepository;
    private final StatisticsWebClient statisticsWebClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    String baseUrl = "http://localhost:9090";

    @Override
    public EventFullDto create(Integer userId, Integer catId, Event event) {
        if (event.getEventDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Start is in past");
        }
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
        List<Event> events = eventRepository.findByInitiatorId(userId, page);
        List<Event> eventsWithViews = getEventViewsList(events);
        return EventMapper.toEventShortDtoList(eventsWithViews);
    }

    @Override
    public EventFullDto findByUserIdAndEventId(Integer userId, Integer eventId) {
        Optional<Event> eventOptional = Optional.of(eventRepository.findByIdAndInitiatorId(eventId, userId));
        Event event = eventOptional.get();
        event.setViews(getViews(event));
        return EventMapper.eventToEventFullDto(event);


    }

    @Override
    public EventFullDto patchByUserIdAndEventId(Integer userId, Integer eventId, String stateAction,
                                                UpdateEvent updateEvent) {
        Optional<Event> optionalEvent = Optional.of(eventRepository.findByIdAndInitiatorId(eventId, userId));

        if (updateEvent.getEventDate() != null) {
            if (LocalDateTime.parse(updateEvent.getEventDate(), formatter).isBefore(LocalDateTime.now().plusHours(2))) {
                throw new BadRequestException("Start is in past");
            }
        }

        Event eventToSave = optionalEvent.get();

        if (eventToSave.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event already published");
        }
        if (!optionalEvent.get().getInitiator().getId().equals(userId)) {
            throw new BadRequestException("User is not initiator");
        }

        if (stateAction != null) {
            if (eventToSave.getState().equals(State.CANCELED) && stateAction.equals("SEND_TO_REVIEW")) {
                eventToSave.setState(State.PENDING);
                eventRepository.save(eventToSave);
                return EventMapper.eventToEventFullDto(eventToSave);
            }
        }
        updateFields(stateAction, eventToSave, updateEvent);

        eventToSave.setViews(getViews(eventToSave));
        eventRepository.save(eventToSave);
        return EventMapper.eventToEventFullDto(eventToSave);
    }

    @Override
    public EventFullDto patchEvent(Integer eventId, String stateAction,
                                   UpdateEvent updateEvent) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        Event eventToSave;
        if (optionalEvent.isPresent()) {
            eventToSave = optionalEvent.get();
        } else {
            throw new NotFoundException("Event not found");
        }

        if (updateEvent.getEventDate() != null) {
            if (LocalDateTime.parse(updateEvent.getEventDate(), formatter).isBefore(LocalDateTime.now().plusHours(1))) {
                throw new BadRequestException("Start is in past");
            }
        }

        if (stateAction != null) {
            if (eventToSave.getState().equals(State.PUBLISHED) && stateAction.equals("PUBLISH_EVENT")) {
                throw new ConflictException("Event already published");
            }
            if (eventToSave.getState().equals(State.CANCELED) && stateAction.equals("PUBLISH_EVENT")) {
                throw new ConflictException("Event publish canceled");
            }
            if (eventToSave.getState().equals(State.PUBLISHED) && stateAction.equals("REJECT_EVENT")) {
                throw new ConflictException("Event already published");
            }
            if (stateAction.equals("REJECT_EVENT")) {
                eventToSave.setState(State.CANCELED);
                eventRepository.save(eventToSave);
                return EventMapper.eventToEventFullDto(eventToSave);
            }
        }
        updateFields(stateAction, eventToSave, updateEvent);

        eventToSave.setViews(getViews(eventToSave));
        eventRepository.save(eventToSave);
        return EventMapper.eventToEventFullDto(eventToSave);
    }

    @Override
    public List<EventFullDto> findEventsByInitiatorIdsAndStatesAndCategoriesIsAfterStartIsBeforeEnd(
            Integer[] userIds, State[] states, Integer[] categoryIds, String start, String end,
            Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size);
        List<Event> events;
        if (start != null && end != null) {
            LocalDateTime startEvens = LocalDateTime.parse(start, formatter);
            LocalDateTime endEvens = LocalDateTime.parse(end, formatter);
            events = eventRepository
                    .findEventsByInitiatorIdInAndStateInAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
                            userIds, states, categoryIds, startEvens, endEvens, page);
        } else {
            if (userIds != null) {
                events = eventRepository.findAllByCategoryIdInAndEventDateIsAfter(categoryIds, LocalDateTime.now(), page);
                return EventMapper.toEventFullDtoList(events);
            }
            if (categoryIds != null) {
                events = eventRepository.findAllByStateInAndEventDateIsAfter(states, LocalDateTime.now(), page);
            } else {
                events = eventRepository.findAllByEventDateIsAfter(LocalDateTime.now(), page);
            }
        }

        List<Event> eventsWithViews = getEventViewsList(events);
        return EventMapper.toEventFullDtoList(eventsWithViews);
    }

    @Override
    public EventFullDto findById(Integer eventId, HttpServletRequest request) {
        Event event = eventRepository.findByIdAndStateIs(eventId, State.PUBLISHED);
        if (event == null) {
            throw new NotFoundException("Event not found");
        }

        event.setViews(getViews(event));
        statisticsWebClient.saveHit(baseUrl + "/hit", getStatsDtoToSave(request));
        return EventMapper.eventToEventFullDto(event);
    }

    @Override
    public List<EventShortDto> searchEvents(String query, Integer[] categoryIds, boolean pais, String start,
                                            String end, boolean onlyAvailable, String sort, Integer from,
                                            Integer size, HttpServletRequest request) {


        Pageable pageable = PageRequest.of(from, size);
        List<Event> eventList;
        List<Event> sortList = new ArrayList<>();

        if (start != null && end != null) {
            LocalDateTime startEvens = LocalDateTime.parse(start, formatter);
            LocalDateTime endEvens = LocalDateTime.parse(end, formatter);
            if (endEvens.isBefore(startEvens)) {
                throw new BadRequestException("Start before end");
            }
            eventList = eventRepository
                    .searchAllByAnnotationAndCategoryIdInAndStateIsAndEventDateIsAfterAndEventDateIsBefore(
                            query, categoryIds, State.PUBLISHED, startEvens, endEvens, pageable);
            if (eventList.size() == 0) {
                eventList = eventRepository
                        .searchAllByDescriptionAndCategoryIdInAndStateIsAndEventDateIsAfterAndEventDateIsBefore(
                                query, categoryIds, State.PUBLISHED, startEvens, endEvens, pageable);
            }
        } else {
            if (query != null) {
                eventList = eventRepository.searchAllByAnnotationAndCategoryIdInAndStateIsAndEventDateIsAfter(
                        query, categoryIds, State.PUBLISHED, LocalDateTime.now(), pageable);
                if (eventList.size() == 0) {
                    eventList = eventRepository
                            .searchAllByDescriptionAndCategoryIdInAndStateIsAndEventDateIsAfter(
                                    query, categoryIds, State.PUBLISHED, LocalDateTime.now(), pageable);
                }
            }
            if (categoryIds != null) {
                eventList = eventRepository.findAllByCategoryIdInAndStateIsAndEventDateIsAfter(
                        categoryIds, State.PUBLISHED, LocalDateTime.now(), pageable);
            } else {
                eventList = eventRepository.findAllByStateIsAndEventDateIsAfter(
                        State.PUBLISHED, LocalDateTime.now(), pageable);
            }

        }

        if (sort != null && sort.equals("EVENT_DATE")) {
            eventList = eventList.stream().sorted(Comparator.comparing(Event::getEventDate)).collect(Collectors.toList());
        }

//        if (pais) {
//            for (Event event : eventList) {
//                if (event.isPaid()) {
//                    sortList.add(event);
//                }
//            }
//        } else {
//            for (Event event : eventList) {
//                if (!event.isPaid()) {
//                    sortList.add(event);
//                }
//            }
//        }
//        eventList.clear();
//        eventList.addAll(sortList);

        if (onlyAvailable) {
            for (Event event : eventList) {
                if (event.getConfirmedRequests() < event.getParticipantLimit()) {
                    sortList.add(event);
                }
            }
            eventList.clear();
            eventList.addAll(sortList);
        }

        List<Event> eventsWithViews = getEventViewsList(eventList);
        statisticsWebClient.saveHit(baseUrl + "/hit", getStatsDtoToSave(request));
        return EventMapper.toEventShortDtoList(eventsWithViews);
    }

    private void updateFields(String stateAction, Event oldEvent, UpdateEvent updateEvent) {
        Category newCategory;

        if (stateAction != null) {
            if (stateAction.equals("CANCEL_REVIEW")) oldEvent.setState(State.CANCELED);
            if (stateAction.equals("PUBLISH_EVENT")) oldEvent.setState(State.PUBLISHED);
        }

        if (!oldEvent.getState().equals(State.CANCELED)) {

            if (updateEvent.getAnnotation() != null) oldEvent.setAnnotation(updateEvent.getAnnotation());

            if (updateEvent.getCategory() != null) {
                newCategory = categoryService.findById(oldEvent.getCategory().getId());
                oldEvent.setCategory(newCategory);
            }

            if (updateEvent.getDescription() != null) oldEvent.setDescription(updateEvent.getDescription());

            if (updateEvent.getEventDate() != null && LocalDateTime.parse(updateEvent.getEventDate(), formatter)
                    .isAfter(oldEvent.getEventDate())) {
                oldEvent.setEventDate(LocalDateTime.parse(updateEvent.getEventDate(), formatter));
            }

            if (updateEvent.getLocation() != null && updateEvent.getLocation().getId() != null) {
                oldEvent.setLocation(updateEvent.getLocation());
            }

            if (updateEvent.getPaid() != null) oldEvent.setPaid(Boolean.parseBoolean(updateEvent.getPaid()));

            if (updateEvent.getParticipantLimit() != null)
                oldEvent.setParticipantLimit(updateEvent.getParticipantLimit());

            if (updateEvent.getRequestModeration() != null)
                oldEvent.setRequestModeration(Boolean.parseBoolean(updateEvent.getRequestModeration()));

            if (updateEvent.getTitle() != null) oldEvent.setTitle(updateEvent.getTitle());
        }
    }

    private StatsDtoToSave getStatsDtoToSave(HttpServletRequest request) {
        return new StatsDtoToSave(
                "ewm-main",
                request.getRequestURI(),
                request.getRemoteAddr(),
                LocalDateTime.now().format(formatter)
        );
    }

    private StatsDtoToGetStats getStatsDtoToGetStats(List<String> uris, boolean unique, Integer from, Integer size) {
        return new StatsDtoToGetStats(
                "2020-05-05 00:00:00",
                "2025-01-01 00:00:00",
                uris,
                unique,
                from == null ? 0 : from,
                size == null ? 10 : size
        );
    }

    private Integer getViews(Event event) {
        String eventUri = "/events/" + event.getId();
        StatsDtoToGetStats statsDtoToGetStats = getStatsDtoToGetStats(List.of(eventUri), true, null, null);
        List<StatsDtoToReturn> statsList = statisticsWebClient.getStatistics(baseUrl, statsDtoToGetStats);
        return statsList.size() == 0 ? 0 : statsList.get(0).getHits();
    }

    List<Event> getEventViewsList(List<Event> events) {
        String eventUri = "/events/";
        List<String> uriEventList = events.stream()
                .map(e -> eventUri + e.getId().toString())
                .collect(toList());
        StatsDtoToGetStats statsDtoToGetStats = getStatsDtoToGetStats(uriEventList, true, null, null);
        List<StatsDtoToReturn> statsList = statisticsWebClient.getStatistics(baseUrl, statsDtoToGetStats);

        Map<Integer, Integer> eventViewsMap = getEventHitsMap(statsList, events);

        List<Event> eventWithViews = new ArrayList<>();

        for (Event event: events) {
            if (eventViewsMap.containsKey(event.getId())) {
                event.setViews(eventViewsMap.get(event.getId()));
                eventWithViews.add(event);
            }
        }

        return eventWithViews;
    }

    private Map<Integer, Integer> getEventHitsMap(List<StatsDtoToReturn> hitDtoList, List<Event> events) {
        Map<Integer, Integer> hits = new HashMap<>();
        if (hitDtoList.size() == 0) {
            for (Event event : events) {
                hits.put(event.getId(), 0);
            }
            return hits;
        }
        for (StatsDtoToReturn viewStatsDto : hitDtoList) {
            hits.put(Integer.parseInt(viewStatsDto.getUri().replace("/events/", "")), viewStatsDto.getHits());
        }
        return hits;
    }
}
