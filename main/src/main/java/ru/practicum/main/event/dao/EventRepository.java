package ru.practicum.main.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.category.model.Category;

public interface EventRepository extends JpaRepository<Category, Integer> {

}
