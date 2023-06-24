package ru.practicum.main.user.dto.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.model.Category;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CategoryDto> toCategoryDtoList(Page<Category> categoryPage) {
        return categoryPage.stream()
                .map(CategoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }
}
