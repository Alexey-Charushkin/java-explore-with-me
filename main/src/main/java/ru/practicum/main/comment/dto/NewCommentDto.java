package ru.practicum.main.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class NewCommentDto {
    @NotNull
    private Integer eventId;
    private Integer authorId;
    @NotEmpty
    @NotBlank
    String text;
}
