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
    private String created;
    @NotNull
    private Integer event;
    @NotNull
    private Integer id;
    @NotNull
    private Integer requester;
    @NotBlank
    private String status;
}
