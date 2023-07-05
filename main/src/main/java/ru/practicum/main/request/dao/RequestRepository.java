package ru.practicum.main.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.request.model.EventRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<EventRequest, Integer> {
    EventRequest findByRequesterIdAndEventId(Integer userId, Integer eventId);

    EventRequest findByIdAndRequesterId(Integer requestId, Integer userId);


    List<EventRequest> findAllByRequesterIdAndEventId(Integer userId, Integer eventId);

    List<EventRequest> findByRequesterId(Integer userId);

    List<EventRequest> findByIdIn(List<Integer> ids);
}
