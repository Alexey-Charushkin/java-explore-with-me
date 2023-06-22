package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventShortDto {
    @NotNull
    @NotBlank
    private String annotation; // Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории
    // Краткое описание
    @NotNull
    private CategoryDto category; // категория
    private Integer confirmedRequests; // example: 5 Количество одобренных заявок на участие в данном событии
    @NotNull
    @NotBlank
    private String eventDate; // example: 2024-12-31 15:10:05
    //  Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private Integer id;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private boolean paid; // example: true Нужно ли оплачивать участие
    @NotNull
    @NotBlank
    private String title;
    private Integer views; // Количество просмотрев события
}
