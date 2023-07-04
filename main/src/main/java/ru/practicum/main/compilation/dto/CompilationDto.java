package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public
class CompilationDto {
    private Integer id;
    private List<EventShortDto> events;
    private Boolean pinned;
    private String title;
}
