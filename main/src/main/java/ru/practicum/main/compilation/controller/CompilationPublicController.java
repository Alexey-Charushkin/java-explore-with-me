package ru.practicum.main.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Log4j2
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("compilations")
public class CompilationPublicController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> findAll(@RequestParam(required = false) Boolean pinned,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                        @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get compilations/?pinned= &from= &size= ");
        PageRequest pageable = PageRequest.of(from, size);
        return compilationService.findAll(pinned, pageable);
    }

    @GetMapping("/{compId}")
    public CompilationDto findById(@PathVariable Integer compId) {
        log.info("Get compilations/{compId}", compId);
        return compilationService.findById(compId);
    }


}
