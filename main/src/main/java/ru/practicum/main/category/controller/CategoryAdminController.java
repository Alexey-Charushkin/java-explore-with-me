package ru.practicum.main.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.dto.NewCategoryDto;
import ru.practicum.main.category.mapper.CategoryMapper;
import ru.practicum.main.category.service.CategoryService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.security.InvalidParameterException;

@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("admin/categories")
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto save(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("Post admin/categories");
        return categoryService.save(CategoryMapper.newCategoryDtoToCategory(newCategoryDto));
    }

    @PatchMapping("{catId}")
    public CategoryDto patch(@Positive @PathVariable("catId") Integer catId,
                             @Valid @RequestBody NewCategoryDto newCategoryDto
    ) throws EntityNotFoundException, InvalidParameterException {
        log.info("Patch admin/categories/catId");
        return categoryService.patch(catId, newCategoryDto);
    }

    @DeleteMapping("{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("catId") Integer catId) throws EntityNotFoundException, InvalidParameterException {
        log.info("delete admin/categories/catId");
        categoryService.deleteById(catId);
    }

}
