package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.service.RequestService;

import javax.validation.constraints.Positive;
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
                                        @RequestParam(name = "eventId", required = false) Integer eventId) {
        try {
            log.info("post {userId}/requests?eventId= ");
            return requestService.save(userId, eventId);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException("Not found");
        }
    }

    @GetMapping("{userId}/requests")
    public List<ParticipationRequestDto> finById(@Positive @PathVariable Integer userId) {
        log.info("Get /users/{userId}/requests/");
        return requestService.findById(userId);
    }

    @PatchMapping("{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequestByOwner(@Positive @PathVariable Integer userId,
                                                        @Positive @PathVariable Integer requestId
    ) {
        log.info("Patch /users/{userId}/requests/{eventId}");
        return requestService.cancelEventRequest(userId, requestId);
    }

}
