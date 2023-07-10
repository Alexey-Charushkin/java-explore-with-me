package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
class CommentPublicController {
    private final CommentService commentService;

    @GetMapping("/comments/{commentId}")
    public CommentDto getById(@PathVariable @Positive Integer commentId) {
        log.info("Patch /users/{userId}/comments/{commentId}");
        return commentService.getById(commentId);
    }

    @GetMapping("/{eventId}/comments")
    public List<CommentDto> getByEventIdAndState(@PathVariable @Positive Integer eventId,
                                                 @RequestParam(name = "state", required = false) String state,
                                                 @RequestParam(name = "start", required = false) String start,
                                                 @RequestParam(name = "end", required = false) String end,
                                                 @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get /events/{eventId}/comments");
        return commentService.findAllByEventIdAndRequestParam(eventId, state, start, end, from, size);
    }
}
