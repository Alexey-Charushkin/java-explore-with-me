package ru.practicum.main.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.request.dto.ParticipationRequestDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {
   // description: Результат подтверждения/отклонения заявок на участие в событии

    private ParticipationRequestDto confirmedRequests; // Заявка на участие в событии

    private ParticipationRequestDto rejectedRequests;

}
