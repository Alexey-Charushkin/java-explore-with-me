package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/{userId}/comments")
class CommentAdminController {
    private final CommentService commentService;

//    @PatchMapping("/{commentId}")
//    public CommentDto patchByAdmin(@PathVariable @Positive Integer userId,
//                                   @PathVariable @Positive Integer commentId,
//                                   @PathVariable String state) {
//        log.info("Patch /admin/{userId}/comments/{commentId}");
//        return commentService.patchByAdmin(userId, commentId, state);
//    }
//
//    @GetMapping("/{userId}")
//    public List<CommentDto> getComments(@PathVariable @Positive Integer adminId,
//                                        @PathVariable @Positive Integer userId,
//                                        @RequestParam(required = false) Long eventId,
//                                        @RequestParam(required = false) String text,
//                                        @RequestParam(required = false) String start,
//                                        @RequestParam(required = false) String end,
//                                        @RequestParam(required = false) String sort,
//                                        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
//                                        @Positive @RequestParam(defaultValue = "10") Integer size) {
//        log.info("Get /admin/{userId}/comments/{commentId}");
//        return commentService.getCommentsByAdmin(userId, eventId, text, start, end, sort, from, size);
//    }
}
