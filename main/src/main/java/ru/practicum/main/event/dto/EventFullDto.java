package ru.practicum.main.event.dto;

import lombok.*;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.user.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    @NotNull
    @NotBlank
    private String annotation; // example: Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории
    // Краткое описание
    @NotNull
    @NotBlank
    private CategoryDto category;

    private Integer confirmedRequests; // Количество одобренных заявок на участие в данном событии
    private String createdOn; // example: 2022-09-06 11:00:23
    // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description; // описание события
    @NotNull
    @NotBlank
    private String eventDate; // example: 2024-12-31 15:10:05
    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")

    private Integer id;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Location location;
    @NotNull
    private boolean paid; // Нужно ли оплачивать участие
    private Integer participantLimit; // example: 10 default: 0
    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private String publishedOn; // example: 2022-09-06 15:10:05
    // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    private boolean requestModeration; // example: true default: true Нужна ли пре-модерация заявок на участие
    private String state; // example: PUBLISHED   Список состояний жизненного цикла события  Enum:
    @NotNull
    @NotBlank
    private String title;
    private Integer views;
}
