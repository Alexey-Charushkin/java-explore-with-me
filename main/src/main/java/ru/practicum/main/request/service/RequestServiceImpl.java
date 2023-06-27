package ru.practicum.main.request.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.service.EventService;
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
import ru.practicum.main.user.service.UserService;

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
        EventRequestStatus status = EventRequestStatus.PENDING;

        EventRequest oldEventRequest = requestRepository.findByRequesterAndEvent(userId, eventId);

        if (oldEventRequest != null) {
            throw new ConflictException("This request found");
        }
        if (event.getState().equals(State.PENDING) || event.getState().equals(State.CANCELED)) {
            throw new ConflictException("Event is not published");
        }
        if (event.getInitiator().getId() == userId) {
            throw new ConflictException("Initiator of event dont add event request");
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("Participant limit is full");
        }
//        if (event.isRequestModeration() == false) {
//            status = EventRequestStatus.CONFIRMED;
//        }

        EventRequest eventRequest = new EventRequest(
                LocalDateTime.now(),
                event.getId(),
                user.getId()
        );
        eventRequest.setStatus(status);

        return RequestMapper.toParticipationRequestDto(requestRepository.save(eventRequest));
    }

    @Override
    public List<ParticipationRequestDto> findById(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        return RequestMapper.toParticipationRequestDtoList(requestRepository.findByRequester(userId));
    }

    @Override
    public ParticipationRequestDto cancelEventRequest(Integer userId, Integer requestId) {
        EventRequest eventRequest = requestRepository.findByIdAndRequester(requestId, userId);
        if (eventRequest == null) {
            throw new NotFoundException("EventRequest not found");
        }
        eventRequest.setStatus(EventRequestStatus.CANCELED);
        requestRepository.save(eventRequest);
        return RequestMapper.toParticipationRequestDto(eventRequest);
    }

    @Override
    public List<ParticipationRequestDto> findRequestsByUserIdAndEventId(Integer userId, Integer eventId) {
        return RequestMapper.toParticipationRequestDtoList(requestRepository.findAllByRequesterAndEvent(userId + 1, eventId));
    }

    @Override
    public EventRequestStatusUpdateResult patchRequestsByUserIdAndEventId(Integer userId, Integer eventId, EventRequestStatusUpdateRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
        List<EventRequest> eventRequestList = requestRepository.findByIdIn((updateRequest.getRequestIds()));
        List<EventRequest> confirmedRequests = new ArrayList<>();
        List<EventRequest> rejectedRequests = new ArrayList<>();
        EventRequestStatusUpdateResult updateResult = new EventRequestStatusUpdateResult();


        if (event.getParticipantLimit() == 0) {
            confirmedRequests.addAll(eventRequestList);
            updateResult.setConfirmedRequests(RequestMapper.toParticipationRequestDtoList(confirmedRequests));
            return updateResult;
        }
        for (EventRequest eventRequest : eventRequestList) {
            if (event.getConfirmedRequests() <= event.getParticipantLimit()) {
                if (eventRequest.getStatus().equals(EventRequestStatus.PENDING)) {
                    eventRequest.setStatus(EventRequestStatus.CONFIRMED);
                    confirmedRequests.add(eventRequest);
                    event.setParticipantLimit(event.getParticipantLimit() + 1);
                }
            } else {
                eventRequest.setStatus(EventRequestStatus.REJECTED);
                rejectedRequests.add(eventRequest);
            }
        }
        updateResult.setConfirmedRequests(RequestMapper.toParticipationRequestDtoList(confirmedRequests));
        updateResult.setRejectedRequests(RequestMapper.toParticipationRequestDtoList(rejectedRequests));

        return updateResult;
    }

//    нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
//    статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
//    если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
}


