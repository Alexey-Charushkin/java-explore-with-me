package ru.practicum.main.event.dto;

import lombok.*;
import ru.practicum.main.locations.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;  // example: Сплав на байдарках похож на полет. Краткое описание события
    @NotNull
    private Integer category; // id категории к которой относится событие
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;  // полное описание события
    @NotNull
    private LocalDateTime eventDate;  // Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @NotNull
    private Location location;
    private String paid; // нужно ли платить
    private Integer participantLimit; // example: 10 default: 0 Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private String requestModeration; // Нужна ли пре-модерация заявок на участие.
    // Если true, то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
    @NotBlank
    @Size(min = 3, max = 120)
    private String title; // example: Сплав на байдарках Заголовок события
}
