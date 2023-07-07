package ru.practicum.main.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@RequiredArgsConstructor
public class NewCommentDto {
    @NotEmpty
    private Integer eventId;
    @NotEmpty
    private Integer authorId;
    @NotEmpty
    @NotBlank
    String text;
}
