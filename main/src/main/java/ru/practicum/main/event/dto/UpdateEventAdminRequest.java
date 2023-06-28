package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.locations.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminRequest {
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;
    private Integer category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private String stateAction;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    public boolean isPaid() {
        return paid;
    }

    public boolean isRequestModeration() {
        return requestModeration;
    }
}
