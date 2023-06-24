package ru.practicum.main.category.service;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.model.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto save(Category category);

    CategoryDto patch(Integer catId, NewCategoryDto newCategoryDto);

    CategoryDto getById(Integer catId);

    List<CategoryDto> getCategories(Integer from, Integer size);

    void deleteById(Integer catId);
}
