package ru.practicum.main.compilation.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto save(NewCompilationDto newCompilationDto);

    void deleteCompilation(Integer compId);

    CompilationDto updateCompilation(Integer compId, NewCompilationDto newCompilationDto);

    CompilationDto findById(Integer compId);

    List<CompilationDto> findAll(boolean pinned, Pageable pageable);


}
