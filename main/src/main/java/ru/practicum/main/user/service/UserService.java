package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

import java.util.List;

public interface UserService {
    UserDto save(User user);

    List<UserDto> getUsersByIds(Long[] ids, Integer from, Integer size);

    void delete(Long userId);
}
