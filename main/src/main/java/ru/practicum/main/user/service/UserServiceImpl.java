package ru.practicum.main.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.practicum.main.user.dao.UserRepository;
import ru.practicum.main.user.model.User;

@Getter
@Setter
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
