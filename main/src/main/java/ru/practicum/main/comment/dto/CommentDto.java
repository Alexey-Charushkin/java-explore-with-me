package ru.practicum.main.comment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentDto {
    private Integer id;
    private Integer eventId;
    private String authorName;
    String text;
    LocalDateTime created;
}
