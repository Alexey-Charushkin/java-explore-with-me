package ru.practicum.main.compilation.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.compilation.dao.CompilationRepository;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.compilation.dto.NewCompilationDto;
import ru.practicum.main.compilation.mapper.CompilationMapper;
import ru.practicum.main.compilation.model.Compilation;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Log4j2
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public CompilationDto save(NewCompilationDto newCompilationDto) {
      //        if (newCompilationDto.getTitle() == null) {
//            throw new InvalidParameterException("Поле Title должно быть заполнено.");
//        }
//        if (newCompilationDto.getTitle().isBlank()) {
//            throw new InvalidParameterException("Поле Title не должно быть пустым.");
//        }
//        if (newCompilationDto.getPinned() == null) {
//            newCompilationDto.setPinned(false);
//        }
        List<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
        return CompilationMapper.toCompilationDto(
                compilationRepository.save(CompilationMapper.toCompilation(newCompilationDto, events)));
    }

    @Override
    public void deleteCompilation(Integer compId) {
              compilationRepository.findById(compId)
                      .orElseThrow(() -> new NotFoundException("Compilation not found"));
        compilationRepository.deleteById(compId);
    }

    @Override
       public CompilationDto updateCompilation(Integer compId, NewCompilationDto newCompilationDto) {

        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Compilation not found"));
        if (newCompilationDto.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllByIdIn(newCompilationDto.getEvents()));
        }
            compilation.setPinned(newCompilationDto.isPinned());
        if (newCompilationDto.getTitle() != null) {
            compilation.setTitle(newCompilationDto.getTitle());
        }
        compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> findAll(boolean pinned, Pageable pageable) {
        log.info("Call #CompilationsService#findAllCompilations# pinned: {}, pageable: {}", pinned, pageable);
        return compilationRepository.findAllByPinnedIs(pinned, pageable).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());

    }

    @Override
    public CompilationDto findById(Integer compId) {
        log.info("Call #CompilationsService#findCompilationsById# compId: {}", compId);
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        return CompilationMapper.toCompilationDto(compilation);
    }
}
