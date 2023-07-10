package ru.practicum.main.comment.dto;

import lombok.*;
import ru.practicum.main.comment.controller.CreateComment;
import ru.practicum.main.comment.controller.UpdateComment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class NewCommentDto {
    @NotNull(groups = {CreateComment.class})
    private Integer eventId;
    private Integer authorId;
    @NotEmpty (groups = {CreateComment.class, UpdateComment.class})
    @NotBlank (groups = {CreateComment.class, UpdateComment.class})
    String text;
}
