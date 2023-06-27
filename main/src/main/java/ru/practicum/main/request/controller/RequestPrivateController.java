package ru.practicum.main.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public ParticipationRequestDto save(@Valid @RequestBody ParticipationRequestDto participationRequestDto) {
        log.info("post {userId}/requests");
        return requestService.save(RequestMapper.toEventRequest(participationRequestDto));
    }
//
//    @GetMapping
//    public List<UserDto> getUsersByIds(@RequestParam(name = "ids", required = false) Integer[] ids,
//                                       @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
//                                       @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
//        log.info("Get admin/users/?ids={} &from={} &size={}", ids, from, size);
//        return userService.getUsersByIds(ids, from, size);
//    }
//
//    @DeleteMapping("{userId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@Positive @PathVariable("userId") Integer userId) throws EntityNotFoundException, InvalidParameterException {
//        log.info("delete admin/users/userId");
//        userService.delete(userId);
//    }
//
}
