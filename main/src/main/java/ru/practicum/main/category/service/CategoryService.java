package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.model.Category;

public interface CategoryService {
    CategoryDto save(Category category);
}
