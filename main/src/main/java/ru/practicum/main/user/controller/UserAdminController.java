package ru.practicum.main.user.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.NewUserRequest;

@Controller
@Log4j2
@RequestMapping("admin/users")
public class UserAdminController {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void save(@RequestBody NewUserRequest newUserRequest) {
        log.info("post admin/users");
        userService.save(newUserRequest);
    }


}
