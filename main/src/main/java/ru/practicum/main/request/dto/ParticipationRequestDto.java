package ru.practicum.main.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private String created; // example: 2022-09-06T21:10:05.432 Дата и время создания заявки
    private Integer event; // Идентификатор события
    private Integer id; // Идентификатор заявки
    private Integer requester; // Идентификатор пользователя, отправившего заявку
    private String status; // example: PENDING Статус заявки
}
