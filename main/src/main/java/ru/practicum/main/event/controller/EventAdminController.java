package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;


@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("admin/events")
public class EventAdminController {

    private final EventService eventService;



//    @GetMapping
//    public List<UserDto> getUsersByIds(@RequestParam(name = "ids", required = false) Long[] ids,
//                                       @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
//                                       @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
//        log.info("Get admin/users/?ids={} &from={} &size={}", ids, from, size);
//        return userService.getUsersByIds(ids, from, size);
//    }
//
//    @DeleteMapping("{userId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@Positive @PathVariable("userId") Long userId) throws EntityNotFoundException, InvalidParameterException {
//        log.info("delete admin/users/userId");
//        userService.delete(userId);
//    }

}
