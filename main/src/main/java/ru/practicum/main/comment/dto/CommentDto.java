package ru.practicum.main.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Integer id;
    private String eventAnnotation;
    private String authorName;
    String text;
    String created;
    String state;
}
