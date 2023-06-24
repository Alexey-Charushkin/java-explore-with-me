package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.format.DateTimeFormatter;

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
}
//{
//        "annotation": "Сплав на байдарках похож на полет.",
//        "category": 2,
//        "description": "Сплав на байдарках похож на полет. На спокойной воде — это парение. На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, феерические эмоции, яркие впечатления.",
//        "eventDate": "2024-12-31 15:10:05",
//        "location": {
//        "lat": 55.754167,
//        "lon": 37.62
//        },
//        "paid": true,
//        "participantLimit": 10,
//        "requestModeration": false,
//        "title": "Сплав на байдарках"
//        }