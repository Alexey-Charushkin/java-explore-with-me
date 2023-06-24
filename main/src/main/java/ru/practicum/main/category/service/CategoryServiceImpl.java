package ru.practicum.main.category.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.dao.CategoryRepository;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto save(Category category) {
        categoryRepository.save(category);
        return CategoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public CategoryDto patch(Integer catId, NewCategoryDto newCategoryDto) {
        Category category = getById(catId);
            category.setName(newCategoryDto.getName());
            return CategoryMapper.categoryToCategoryDto(category);

    }

    public CategoryDto findById(Integer catId) {
        return CategoryMapper.categoryToCategoryDto(getById(catId));
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size);
        return CategoryMapper.toCategoryDtoList(categoryRepository.findAll(page));
    }

    @Override
    public void deleteById(Integer catId) {
        //  Обратите внимание: с категорией не должно быть связано ни одного события.
        categoryRepository.delete(getById(catId));
    }

    private Category getById(Integer catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id " + catId + " was not found"));
        return category;
    }
}
