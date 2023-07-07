package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/comments")
class CommentPrivateController {
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentResponseDto addComment(@PathVariable @Positive Long userId,
                                         @RequestBody @Valid CommentRequestDto commentRequestDto) throws InvalidParameterException, ConflictException {
        log.info("Call#CommentUserController#addComment# userId: {}, commentRequestDto: {}", userId, commentRequestDto);
        return commentService.addComment(userId, commentRequestDto);
    }

    @PatchMapping("/{commentId}")
    public CommentResponseDto updateComment(@PathVariable @Positive Long userId,
                                            @PathVariable @Positive Long commentId,
                                            @RequestBody @Valid CommentRequestDto commentRequestDto) throws InvalidParameterException {
        log.info("Call#CommentUserController#updateComment# userId: {},  commentId: {} commentRequestDto: {}", userId, commentId, commentRequestDto);
        return commentService.updateComment(userId, commentId, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public CommentResponseDto deleteComment(@PathVariable @Positive Long userId,
                                            @PathVariable @Positive Long commentId) throws InvalidParameterException, ConflictException {
        log.info("Call#CommentUserController#deleteComment# userId: {},  commentId: {}", userId, commentId);
        return commentService.deleteComment(userId, commentId);
    }

    @GetMapping("/{commentId}")
    public CommentResponseDto getComment(@PathVariable @Positive Long userId,
                                         @PathVariable @Positive Long commentId) throws InvalidParameterException {
        log.info("Call#CommentUserController#getComment# userId: {},  commentId: {}", userId, commentId);
        return commentService.getComment(userId, commentId);
    }

}
