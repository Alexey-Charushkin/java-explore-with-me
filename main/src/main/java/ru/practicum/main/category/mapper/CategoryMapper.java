package ru.practicum.main.category.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.model.Category;

@UtilityClass
public class CategoryMapper {
    public Category newCategoryDtoToCategory(NewCategoryDto newCategoryDto) {
        return new Category(
                newCategoryDto.getName()
        );
    }

    public CategoryDto categoryToCategoryDto(Category category) {
        return new CategoryDto(
             category.getId(),
             category.getName()
        );
    }
}
