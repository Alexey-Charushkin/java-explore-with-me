package ru.practicum.main.comment.mapper;

import lombok.experimental.UtilityClass;

@UtilityClass
class CommentMapper {

        public  CommentResponseDto toCommentResponseDto(Comment comment) {
            return new CommentResponseDto(
                    comment.getId(),
                    comment.getEvent().getId(),
                    comment.getText(),
                    comment.getUser().getName(),
                    comment.getCreated()
            );
        }
}
