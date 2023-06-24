package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

import java.util.List;

public interface EventService {
//    UserDto save(User user);
//
//    List<UserDto> getUsersByIds(Long[] ids, Integer from, Integer size);
//
//    void delete(Long userId);
     EventFullDto create(Integer userId, Integer catId, Event event);
}
