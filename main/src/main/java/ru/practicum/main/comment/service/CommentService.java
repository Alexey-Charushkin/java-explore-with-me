package ru.practicum.main.comment.service;

import org.springframework.data.repository.core.support.IncompleteRepositoryCompositionException;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.dto.CommentDto;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {

    CommentDto save(NewCommentDto newCommentDto);

    CommentDto patchByUser(Integer userId, Integer commentId, NewCommentDto newCommentDto);

    CommentDto delete(Integer userId, Integer commentId);

    CommentDto getById(Integer commentId);

    CommentDto getByUserIdAndCommentId(Integer userId, Integer commentId);

    List<CommentDto> findAllByUserIdAndRequestParam(Integer userId, String state, String start, String end,
                                                    Integer from, Integer size);
//
//    List<CommentDto> getAllByEventId(Integer eventId, String text, LocalDateTime rangeStart, LocalDateTime rangeEnd,
//                                     String sort, Integer from, Integer size);
//
//    CommentDto patchByAdmin(Integer userId, Integer commentId);
//
//    CommentDto publishComment(Integer userId, Integer commentId);
//
//    List<CommentDto> getCommentsByAdmin(Integer userId, Integer eventId, String text, LocalDateTime start,
//                                        LocalDateTime end, String sort, Integer from, Integer size);
//
}
