package ru.practicum.main.category.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.dao.CategoryRepository;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static ru.practicum.main.request.model.EventRequestStatus.CONFIRMED;

@Getter
@Setter
@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDto save(Category category) {
        try {
            categoryRepository.save(category);
            return CategoryMapper.categoryToCategoryDto(category);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Category with this name is already exist");
        }
    }

    @Override
    public CategoryDto patch(Integer catId, NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

            List<Category> categories = categoryRepository.findAll();
            boolean isDuplicate = categories.stream()
                    .anyMatch(c -> c.getId() != catId && c.getName().equals(newCategoryDto.getName()));
            if (isDuplicate) {
                throw new ConflictException("Category is duplicate");
            }

        category.setName(newCategoryDto.getName());
        return CategoryMapper.categoryToCategoryDto(category);

    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable page = PageRequest.of(from, size);
        return CategoryMapper.toCategoryDtoList(categoryRepository.findAll(page));
    }

    @Override
    public void deleteById(Integer catId) {
       List<Event> events = eventRepository.findAllByCategoryId(catId);
        if (events.size() != 0) {
            boolean isConfirmedState = events.stream()
                    .anyMatch(e -> e.getState().equals(CONFIRMED));
            if (!isConfirmedState) {
                throw new ConflictException("Not delete. Category contains events");
            }
        }
        categoryRepository.delete(findById(catId));
    }

    @Override
    public Category findById(Integer catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category with id " + catId + " was not found"));
        return category;
    }
}
