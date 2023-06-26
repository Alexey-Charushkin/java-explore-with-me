package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.service.EventService;

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
    public EventFullDto finById(@Positive @PathVariable Integer eventId) {
        log.info("Get events/eventId}");
        return eventService.findById(eventId);
    }

}
