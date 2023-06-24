package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/users")
class EventPrivateController {

    private final EventService eventService;

    @PostMapping("{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto save(@Positive @PathVariable Integer userId,
                             @Valid @RequestBody NewEventDto newEventDto) {
        log.info("Post /users/{userId}/events");

        return eventService.create(userId, newEventDto.getCategory(), EventMapper.newEventDtoToEvent(newEventDto));
    }

    @GetMapping("{userId}/events")
    public List<EventShortDto> finById(@Positive @PathVariable Integer userId,
                                       @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                       @RequestParam(name = "size", defaultValue = "10") @Positive Integer size
    ) {
        log.info("Get /users/{userId}/events/?from= &size= ");

        return eventService.findById(userId, from, size);
    }
}