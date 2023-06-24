package ru.practicum.main.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.event.model.Event;


import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
  List<Event> findByInitiatorId(Integer id, Pageable pageable);
}
