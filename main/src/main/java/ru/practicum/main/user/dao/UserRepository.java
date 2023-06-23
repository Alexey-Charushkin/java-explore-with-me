package ru.practicum.main.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

