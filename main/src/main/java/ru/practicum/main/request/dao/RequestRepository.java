package ru.practicum.main.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.request.model.EventRequest;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface RequestRepository extends JpaRepository <EventRequest, Integer> {
    EventRequest findByRequesterAndEvent(Integer userId, Integer eventId);

    EventRequest findByIdAndRequester(Integer requestId, Integer userId);


   List <EventRequest> findAllByRequesterAndEvent(Integer userId, Integer eventId);
    List<EventRequest> findByRequester(Integer userId);
}
