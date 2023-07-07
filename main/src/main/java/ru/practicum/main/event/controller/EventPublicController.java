package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("events")
public class EventPublicController {

    private final EventService eventService;


    @GetMapping("{eventId}")
    public EventFullDto finById(@Positive @PathVariable Integer eventId, HttpServletRequest httpServletRequest) {
        log.info("Get events/eventId}");
        return eventService.findById(eventId, httpServletRequest);
    }

    @GetMapping()
    public List<EventShortDto> searchEvents(@RequestParam(value = "text", required = false) String query,
                                            @RequestParam(name = "categories", required = false) Integer[] categoryIds,
                                            @RequestParam(name = "paid", required = false) boolean pais,
                                            @RequestParam(name = "rangeStart", required = false) String start,
                                            @RequestParam(name = "rangeEnd", required = false) String end,
                                            @RequestParam(name = "onlyAvailable", required = false) boolean onlyAvailable,
                                            @RequestParam(name = "sort", required = false) String sort,
                                            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size,
                                            HttpServletRequest httpServletRequest) {
        log.info("Get =search");
        return eventService.searchEvents(query, categoryIds, pais, start,
                end, onlyAvailable, sort, from, size, httpServletRequest);
    }

}
