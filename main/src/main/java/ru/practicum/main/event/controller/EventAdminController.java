package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("admin/events")
public class EventAdminController {

    private final EventService eventService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping
    public List<EventFullDto> getEventsByIds(@RequestParam(name = "users", required = false) Integer[] userIds,
                                             @RequestParam(name = "states", required = false) State[] states,
                                             @RequestParam(name = "categories", required = false) Integer[] categoryIds,
                                             @RequestParam(name = "rangeStart", required = false) String start,
                                             @RequestParam(name = "rangeEnd", required = false) String end,
                                             @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                             @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get /admin/events?users= &states= &categories= &rangeStart= &rangeEnd=  &from= &size= ");
        return eventService.findEventsByInitiatorIdsAndStatesAndCategoriesIsAfterStartIsBeforeEnd(
                userIds, states, categoryIds, LocalDateTime.parse(start, formatter),
                LocalDateTime.parse(end, formatter), from, size);
    }

    @PatchMapping("{eventId}")
    public EventFullDto patchEvent(@Positive @PathVariable Integer eventId,
                                   @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequestRequest) {
        log.info("Patch admin/events/{eventId}");
        return eventService.patchEvent(eventId,
                updateEventAdminRequestRequest.getStateAction(),
                EventMapper.updateEventAdminRequestToEvent(updateEventAdminRequestRequest));
    }
}
