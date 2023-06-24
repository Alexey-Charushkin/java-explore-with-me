package ru.practicum.main.event.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.event.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
