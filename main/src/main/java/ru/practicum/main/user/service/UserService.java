package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

public interface UserService {
   UserDto save(User user);
}
