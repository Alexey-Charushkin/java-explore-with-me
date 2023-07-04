package ru.practicum.main.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(null,
                events,
                newCompilationDto.isPinned(),
                newCompilationDto.getTitle());
    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        List<EventShortDto> shortEvents = compilation.getEvents().stream()
                .map(e -> EventMapper. eventToEventShortDto(e))
                .collect(Collectors.toList());
        return new CompilationDto(compilation.getId(),
                shortEvents,
                compilation.getPinned(),
                compilation.getTitle());
    }
}
