package ru.practicum.main.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.request.model.EventRequest;

public interface RequestRepository extends JpaRepository <EventRequest, Integer> {

}
