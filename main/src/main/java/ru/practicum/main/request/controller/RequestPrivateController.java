package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.UpdateEventUserRequest;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.service.RequestService;
import ru.practicum.main.user.dto.NewUserDto;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.security.InvalidParameterException;
import java.util.List;


@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("users")
public class RequestPrivateController {

    private final RequestService requestService;

    @PostMapping("{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto save(@Positive @PathVariable(name = "userId") Integer userId,
                                        @RequestParam(name = "eventId") Integer eventId) {
        log.info("post {userId}/requests?eventId= ");
        return requestService.save(userId, eventId);
    }

    @GetMapping("{userId}/requests")
    public List<ParticipationRequestDto> finById(@Positive @PathVariable Integer userId) {
        log.info("Get /users/{userId}/requests/");
        return requestService.findById(userId);
    }

    @PatchMapping("{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto patchByUserIdAndEventId(@Positive @PathVariable Integer userId,
                                                @Positive @PathVariable Integer requestId
                                                ) {
        log.info("Patch /users/{userId}/requests/{eventId}");
        return requestService.cancelEventRequest(userId, requestId);
    }

}
