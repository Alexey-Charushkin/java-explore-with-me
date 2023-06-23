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
import ru.practicum.main.user.dto.NewUserDto;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.mapper.UserMapper;
import ru.practicum.main.user.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.security.InvalidParameterException;
import java.util.List;


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
        log.info("post admin/categories");
        return categoryService.save(CategoryMapper.newCategoryDtoToCategory(newCategoryDto));
    }
//
//    @GetMapping
//    public List<UserDto> getUsersByIds(@RequestParam(name = "ids", required = false) Long[] ids,
//                                       @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
//                                       @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
//        log.info("Get admin/users/?ids={} &from={} &size={}", ids, from, size);
//        return userService.getUsersByIds(ids, from, size);
//    }
//
//    @DeleteMapping("{userId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@Positive @PathVariable("userId") Long userId) throws EntityNotFoundException, InvalidParameterException {
//        log.info("delete admin/users/userId");
//        userService.delete(userId);
//    }

}
