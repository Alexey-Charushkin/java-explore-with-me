package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.service.CategoryService;
import ru.practicum.main.exception.BadRequestException;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.security.InvalidParameterException;
import java.util.List;

@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/categories")
class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("{catId}")
    public CategoryDto getById(@Positive @PathVariable("catId") Integer catId) throws EntityNotFoundException, InvalidParameterException {
        log.info("Get categories/catId");
        return categoryService.getById(catId);
    }

    @GetMapping
    public List<CategoryDto> getCategory(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(name = "size", defaultValue = "10") @Positive Integer size)
            throws BadRequestException {
        log.info("Get categories/?from={} &size={}", from, size);
        return categoryService.getCategories(from, size);
    }

}
