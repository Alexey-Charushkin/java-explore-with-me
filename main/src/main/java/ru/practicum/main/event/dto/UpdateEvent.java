package ru.practicum.main.event.dto;


import ru.practicum.main.locations.model.Location;




public interface UpdateEvent {

    String getEventDate();

    String getAnnotation();

    Integer getCategory();

    String getDescription();

    Location getLocation();

    Integer getParticipantLimit();

    String getRequestModeration();

    String getTitle();

    String getPaid();
}
