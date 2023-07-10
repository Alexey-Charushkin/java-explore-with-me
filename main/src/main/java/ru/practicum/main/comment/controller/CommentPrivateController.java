package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.service.CommentService;
import ru.practicum.main.exception.BadRequestException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users")
class CommentPrivateController {
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/comments")
    public CommentDto save(@PathVariable @Positive Integer userId,
                           @RequestBody @Validated(CreateComment.class) NewCommentDto newCommentDto) {
        try {
            log.info("Post /users/{userId}/comments");
            newCommentDto.setAuthorId(userId);
            return commentService.save(newCommentDto);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new BadRequestException("User not found");
        }
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentDto patchByUser(@PathVariable @Positive Integer userId,
                                  @PathVariable @Positive Integer commentId,
                                  @RequestBody @Validated(UpdateComment.class) NewCommentDto newCommentDto) {
        log.info("Patch /users/{userId}/comments/{commentId}");
        return commentService.patchByUser(userId, commentId, newCommentDto);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public CommentDto delete(@PathVariable @Positive Integer userId,
                             @PathVariable @Positive Integer commentId) {
        log.info("Delete /users/{userId}/comments/{commentId}");
        return commentService.delete(userId, commentId);
    }

    @GetMapping("/{userId}/comments/{eventId}")
    public CommentDto getByUserIdAndCommentId(@PathVariable @Positive Integer userId,
                                              @PathVariable @Positive Integer eventId) {
        log.info("Get /users/{userId}/comments/{commentId}");
        return commentService.getByUserIdAndCommentId(userId, eventId);
    }

    @GetMapping("/{userId}/comments")
    public List<CommentDto> getByUserIdAndState(@PathVariable @Positive Integer userId,
                                                @RequestParam(name = "state", required = false) String state,
                                                @RequestParam(name = "start", required = false) String start,
                                                @RequestParam(name = "end", required = false) String end,
                                                @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get /users/{userId}/comments");
        return commentService.findAllByUserIdAndRequestParam(userId, state, start, end, from, size);
    }
}
