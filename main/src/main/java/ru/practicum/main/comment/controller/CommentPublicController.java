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
@RequestMapping(path = "/events/{eventId}/comments")
class CommentPublicController {
    private final CommentService commentService;

//    @GetMapping()
//    public List<CommentDto> getAllByEventId(@PathVariable @Positive Integer eventId,
//                                            @RequestParam(required = false) String text,
//                                            @RequestParam(required = false) String start,
//                                            @RequestParam(required = false) String end,
//                                            @RequestParam(required = false) String sort,
//                                            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
//                                            @Positive @RequestParam(defaultValue = "10") Integer size) {
//        log.info("Get /events/{eventId}/comments");
//        return commentService.getAllByEventId(eventId, text, start, end, sort, from, size);
//    }
}
