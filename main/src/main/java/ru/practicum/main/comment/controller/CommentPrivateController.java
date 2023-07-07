package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Log4j2
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = "/users/{userId}/comments")
class CommentPrivateController {
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentDto save(@PathVariable @Positive Integer userId,
                           @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Post /users/{userId}/comments");
        newCommentDto.setAuthorId(userId);
        return commentService.save(newCommentDto);
    }

//    @PatchMapping("/{commentId}")
//    public CommentDto patchByUser(@PathVariable @Positive Integer userId,
//                                  @PathVariable @Positive Integer commentId,
//                                  @RequestBody @Valid NewCommentDto newCommentDto) {
//        log.info("Patch /users/{userId}/comments/{commentId}");
//        return commentService.patchByUser(userId, commentId, newCommentDto);
//    }
//
//    @DeleteMapping("/{commentId}")
//    public CommentDto delete(@PathVariable @Positive Integer userId,
//                             @PathVariable @Positive Integer commentId) {
//        log.info("Patch /users/{userId}/comments/{commentId}");
//        return commentService.delete(userId, commentId);
//    }
//
//    @GetMapping("/{commentId}")
//    public CommentDto getById(@PathVariable @Positive Long userId,
//                              @PathVariable @Positive Long commentId) {
//        log.info("Patch /users/{userId}/comments/{commentId}");
//        return commentService.getById(userId, commentId);
//    }

}
