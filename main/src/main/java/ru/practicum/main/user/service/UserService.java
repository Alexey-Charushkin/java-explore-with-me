package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.model.User;

import java.util.List;

public interface UserService {
    UserDto save(User user);
    UserShortDto findById(Integer userId);
    List<UserDto> getUsersByIds(Integer[] ids, Integer from, Integer size);

    void delete(Integer userId);
}
