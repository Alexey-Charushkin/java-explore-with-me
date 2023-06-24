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
    private String annotation;
    @NotNull
    private Integer category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    private String eventDate;
    @NotNull
    private Location location;
    private String paid;
    private Integer participantLimit;
    private String requestModeration;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
