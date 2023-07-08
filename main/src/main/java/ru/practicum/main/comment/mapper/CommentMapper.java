package ru.practicum.main.comment.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.model.Comment;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public
class CommentMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        public CommentDto toCommentDto(Comment comment) {
            return new CommentDto(
                    comment.getId(),
                    comment.getEvent().getAnnotation(),
                    comment.getUser().getName(),
                    comment.getText(),
                    comment.getCreated().format(formatter),
                    comment.getCommentState().toString()
            );
        }

        public List<CommentDto> toCommentDtoList(List<Comment> commentList) {
            return commentList.stream()
                    .map(CommentMapper::toCommentDto)
                    .collect(Collectors.toList());
        }
}
