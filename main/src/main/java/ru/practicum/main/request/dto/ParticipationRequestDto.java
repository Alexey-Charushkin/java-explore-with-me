package ru.practicum.main.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    @NotBlank
    private String created; // example: 2022-09-06T21:10:05.432 Дата и время создания заявки
    @NotNull
    private Integer event; // Идентификатор события
    @NotNull
    private Integer id; // Идентификатор заявки
    @NotNull
    private Integer requester; // Идентификатор пользователя, отправившего заявку
    @NotBlank
    private String status; // example: PENDING Статус заявки
}
