package ru.practicum.main.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.NewUserRequest;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.service.UserService;


@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("admin/users")
public class UserAdminController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody NewUserRequest newUserRequest) {
        log.info("post admin/users");
        userService.save(UserMapper.NewUserRequestToUser(newUserRequest));
    }


}
