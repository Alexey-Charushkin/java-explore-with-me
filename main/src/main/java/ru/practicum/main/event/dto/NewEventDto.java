package ru.practicum.main.event.dto;

import lombok.*;
import ru.practicum.main.locations.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotBlank(groups = {Create.class})
    @Size(groups = {Create.class},min = 20, max = 2000)
    private String annotation;
    @NotNull(groups = {Create.class})
    private Integer category;
    @NotBlank(groups = {Create.class})
    @Size(groups = {Create.class}, min = 20, max = 7000)
    private String description;
    @NotNull(groups = {Create.class})
    private String eventDate;
    @NotNull(groups = {Create.class})
    private Location location;
    private String paid;
    private Integer participantLimit;
    private String requestModeration;
    @NotBlank(groups = {Create.class})
    @Size(groups = {Create.class}, min = 3, max = 120)
    private String title;
}
