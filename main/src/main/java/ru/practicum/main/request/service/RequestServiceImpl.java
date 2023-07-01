package ru.practicum.main.request.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.request.dao.RequestRepository;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.ParticipationRequestDto;
import ru.practicum.main.request.mapper.RequestMapper;
import ru.practicum.main.request.model.EventRequest;
import ru.practicum.main.request.model.EventRequestStatus;
import ru.practicum.main.user.dao.UserRepository;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Log4j2
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    @Override
    public ParticipationRequestDto save(Integer userId, Integer eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        EventRequest oldEventRequest = requestRepository.findByRequesterIdAndEventId(userId, eventId);

        if (oldEventRequest != null) {
            throw new ConflictException("This request found");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event is not published");
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Initiator of event dont add event request");
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit() && event.getParticipantLimit() != 0) {
            throw new ConflictException("Participant limit is full");
        }

        EventRequest request = new EventRequest(
                LocalDateTime.now(),
                event,
                user
        );

        if (!event.isRequestModeration()) {
            if (event.getParticipantLimit() == 0) {
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                eventRepository.save(event);
                request.setStatus(EventRequestStatus.CONFIRMED);
            } else {
                request.setStatus(EventRequestStatus.PENDING);
            }
        }
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    public List<ParticipationRequestDto> findById(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        return RequestMapper.toParticipationRequestDtoList(requestRepository.findByRequesterId(userId));
    }

    @Override
    public ParticipationRequestDto cancelEventRequest(Integer userId, Integer requestId) {
        EventRequest eventRequest = requestRepository.findByIdAndRequesterId(requestId, userId);
        if (eventRequest == null) {
            throw new NotFoundException("EventRequest not found");
        }
        eventRequest.setStatus(EventRequestStatus.CANCELED);
        requestRepository.save(eventRequest);
        return RequestMapper.toParticipationRequestDto(eventRequest);
    }

    @Override
    public List<ParticipationRequestDto> findRequestsByUserIdAndEventId(Integer userId, Integer eventId) {
        return RequestMapper.toParticipationRequestDtoList(requestRepository.findAllByRequesterIdAndEventId(userId + 1, eventId));
    }

    @Override
    public EventRequestStatusUpdateResult patchRequestsByUserIdAndEventId(Integer userId, Integer eventId, EventRequestStatusUpdateRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (!event.getInitiator().getId().equals(userId)) {
            throw new NotFoundException("User is no initiator this event");
        }

//        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
//            throw new ConflictException("Confirmation is not required");
//        }

        List<EventRequest> eventRequestList = requestRepository.findByIdIn((updateRequest.getRequestIds())); // получили список запросов
        List<EventRequest> confirmedRequests = new ArrayList<>();
        List<EventRequest> rejectedRequests = new ArrayList<>();
        EventRequestStatusUpdateResult updateResult = new EventRequestStatusUpdateResult();

        if (updateRequest.getStatus().equals(EventRequestStatus.REJECTED)) {
            for (EventRequest eventRequest : eventRequestList) {

                if (eventRequest.getStatus().equals(EventRequestStatus.CONFIRMED)) {
                    throw new ConflictException("Status is already confirmed");
                }
                eventRequest.setStatus(EventRequestStatus.REJECTED);
                rejectedRequests.add(eventRequest);
            }
        }

        if (event.getParticipantLimit() == 0) {
            confirmedRequests.addAll(eventRequestList);
            updateResult.setConfirmedRequests(RequestMapper.toParticipationRequestDtoList(confirmedRequests));
            return updateResult;
        }

        for (EventRequest eventRequest : eventRequestList) {

            if (event.getConfirmedRequests() <= event.getParticipantLimit()) {

                if (eventRequest.getStatus().equals(EventRequestStatus.PENDING)) {
                  //  if (!eventRequest.getStatus().equals(EventRequestStatus.CONFIRMED)) {
                        eventRequest.setStatus(EventRequestStatus.CONFIRMED);
                        confirmedRequests.add(eventRequest);
                        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                  //  }
                }
            } else {
                eventRequest.setStatus(EventRequestStatus.REJECTED);
                rejectedRequests.add(eventRequest);
            }
        }
        eventRepository.save(event);
        updateResult.setConfirmedRequests(RequestMapper.toParticipationRequestDtoList(confirmedRequests));
        updateResult.setRejectedRequests(RequestMapper.toParticipationRequestDtoList(rejectedRequests));

        return updateResult;
    }
}


