package ru.practicum.main.comment.dto;

import lombok.*;
import ru.practicum.main.compilation.controller.Create;
import ru.practicum.main.compilation.controller.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class NewCommentDto {
    @NotNull(groups = {Create.class})
    private Integer eventId;
    private Integer authorId;
    @NotEmpty (groups = {Create.class, Update.class})
    @NotBlank (groups = {Create.class, Update.class})
    String text;
}
