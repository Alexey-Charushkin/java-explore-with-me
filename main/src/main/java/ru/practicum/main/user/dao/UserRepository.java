package ru.practicum.main.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.user.model.User;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByIdIn(Integer[] ids, Pageable page);
}

