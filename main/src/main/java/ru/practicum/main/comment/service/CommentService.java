package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto save(NewCommentDto newCommentDto);
    CommentDto patchByUser(Integer userId, Integer commentId, NewCommentDto newCommentDto);
    CommentDto delete(Integer userId, Integer commentId);
    CommentDto getById(Integer commentId);
    CommentDto getByUserIdAndCommentId(Integer userId, Integer commentId);
    List<CommentDto> findAllByUserIdAndRequestParam(Integer userId, String state, String start, String end,
                                                    Integer from, Integer size);
    List<CommentDto> findAllByEventIdAndRequestParam(Integer userId, String state, String start, String end,
                                                     Integer from, Integer size);
    List<CommentDto> findAllByUserIdsAndRequestParam(Integer[] ids, String state, String start,
                                                     String end, Integer from, Integer size);
    List<CommentDto> findAllByEventIdsAndRequestParam(Integer[] ids, String state, String start,
                                                      String end, Integer from, Integer size);
    CommentDto patchByAdmin(Integer commentId, String state);
}
