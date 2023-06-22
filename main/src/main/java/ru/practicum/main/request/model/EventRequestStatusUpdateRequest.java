package ru.practicum.main.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds; // Идентификаторы запросов на участие в событии текущего пользователя
    private EventRequestStatus status; // Новый статус запроса на участие в событии текущего пользователя
   // Enum: [ CONFIRMED, REJECTED ]
}
