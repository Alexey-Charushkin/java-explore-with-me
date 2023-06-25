package ru.practicum.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.locations.model.Location;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequest {
    private String annotation;
    private Integer category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private boolean requestModeration;
    private String stateAction;
    private String title;

    public boolean isPaid() {
        return paid;
    }

    public boolean isRequestModeration() {
        return requestModeration;
    }
}
