package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.service.CompilationService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;

@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("admin/compilations")
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Post /admin/compilations");
        return compilationService.save(newCompilationDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer id) {
        log.info("Delete /admin/compilations/{id}");
        compilationService.deleteCompilation(id);
    }

    @PatchMapping("/{id}")
    public CompilationDto update(@PathVariable Integer id,
                                 @Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Patch /admin/compilations");
        return compilationService.updateCompilation(id, newCompilationDto);
    }

}
