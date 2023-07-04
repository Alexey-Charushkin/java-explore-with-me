package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.service.CompilationService;
import ru.practicum.main.exception.BadRequestException;

@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("admin/compilations")
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto save(@Validated({Create.class}) @RequestBody NewCompilationDto newCompilationDto) {
        try {
            log.info("Post /admin/compilations");
            return compilationService.save(newCompilationDto);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException("NewCompilationDto validate exception");
        }

    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Integer compId) {
        log.info("Delete /admin/compilations/{id}");
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable Integer compId,
                                 @Validated(Update.class) @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Patch /admin/compilations");
        return compilationService.updateCompilation(compId, newCompilationDto);
    }

}
