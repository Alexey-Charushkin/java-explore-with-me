package ru.practicum.main.event.dto;

import lombok.*;

import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.UserShortDto;
import ru.practicum.main.locations.model.Location;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {

    private String annotation; // example: Эксклюзивность нашего шоу гарантирует привлечение максимальной зрительской аудитории
    // Краткое описание

    private CategoryDto category;

    private Integer confirmedRequests; // Количество одобренных заявок на участие в данном событии
    private LocalDateTime createdOn; // example: 2022-09-06 11:00:23
    // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description; // описание события

    private LocalDateTime eventDate; // example: 2024-12-31 15:10:05
    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")

    private Integer id;

    private UserShortDto initiator;

    private Location location;

    private boolean paid; // Нужно ли оплачивать участие
    private Integer participantLimit; // example: 10 default: 0
    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private String publishedOn; // example: 2022-09-06 15:10:05
    // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    private boolean requestModeration; // example: true default: true Нужна ли пре-модерация заявок на участие
    private Enum state; // example: PUBLISHED   Список состояний жизненного цикла события  Enum:
    private String title;
    private Integer views;
}
