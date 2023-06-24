package ru.practicum.main.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.dao.UserRepository;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto save(User user) {
        userRepository.save(user);
        return UserMapper.userToUserDto(user);
    }

    @Override
    public User findById(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        return user;
    }

    @Override
    public List<UserDto> getUsersByIds(Integer[] ids, Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size);
        if (ids == null) return UserMapper.pageUserToListUserDto(userRepository.findAll(page));
        if (ids.length == 0) return Collections.emptyList();
        return UserMapper.listUserToListUserDto(userRepository.findByIdIn(ids, page));
    }

    @Override
    public void delete(Integer userId) {
        userRepository.deleteById(userId);
    }
}
