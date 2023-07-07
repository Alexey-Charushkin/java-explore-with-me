package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events/{eventId}/comments")
class CommentJPublicController {
    private final CommentService commentService;

    @GetMapping()
    public List<CommentResponseDto> getComments(@PathVariable @Positive Long eventId,
                                                @RequestParam(required = false) String text,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                @RequestParam(required = false) String sort,
                                                @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                @Positive @RequestParam(defaultValue = "10") Integer size) throws InvalidParameterException {
        log.info("Call#CommentUserController#getComment#  eventId: {}", eventId);
        return commentService.getComments(eventId, text, rangeStart, rangeEnd, sort, from, size);
    }
}
