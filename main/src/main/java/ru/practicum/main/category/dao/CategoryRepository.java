package ru.practicum.main.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
