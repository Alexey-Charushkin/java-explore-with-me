package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.event.service.EventService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("events")
public class EventPublicController {

    private final EventService eventService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping("{eventId}")
    public EventFullDto finById(@Positive @PathVariable Integer eventId) {
        log.info("Get events/eventId}");
        return eventService.findById(eventId);
    }

    @GetMapping()
    public List<EventShortDto> searchEvents(@NotBlank @RequestParam("text") String query,
                                            @RequestParam(name = "categories") Integer[] categoryIds,
                                            @RequestParam(name = "paid") boolean pais,
                                            @RequestParam(name = "rangeStart") String start,
                                            @RequestParam(name = "rangeEnd") String end,
                                            @RequestParam(name = "onlyAvailable") boolean onlyAvailable,
                                            @RequestParam(name = "sort") String sort,
                                            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get =search");
        return eventService.searchEvents(query, categoryIds, pais, LocalDateTime.parse(start, formatter),
                LocalDateTime.parse(end, formatter), onlyAvailable, sort, from, size);
    }
}
