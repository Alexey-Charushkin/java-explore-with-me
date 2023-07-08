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
@RequestMapping(path = "/admin/comments")
class CommentAdminController {
    private final CommentService commentService;

    @PatchMapping("/{commentId}")
    public CommentDto patchByAdmin(@PathVariable @Positive Integer commentId,
                                   @RequestParam String state) {
        log.info("Patch /admin/comments/{commentId}");
        return commentService.patchByAdmin(commentId, state);
    }

    @GetMapping()
    public List<CommentDto> getByUserIdsAndState(@RequestParam(name = "ids", required = false) Integer[] ids,
                                                 @RequestParam(name = "state", required = false) String state,
                                                 @RequestParam(name = "start", required = false) String start,
                                                 @RequestParam(name = "end", required = false) String end,
                                                 @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get /comments");
        return commentService.findAllByUserIdsAndRequestParam(ids, state, start, end, from, size);
    }

    @GetMapping("/events")
    public List<CommentDto> getByEventIdsAndState(@RequestParam(name = "ids", required = false) Integer[] ids,
                                                  @RequestParam(name = "state", required = false) String state,
                                                  @RequestParam(name = "start", required = false) String start,
                                                  @RequestParam(name = "end", required = false) String end,
                                                  @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        log.info("Get /comments/events");
        return commentService.findAllByEventIdsAndRequestParam(ids, state, start, end, from, size);
    }
}
