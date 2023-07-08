package ru.practicum.main.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
