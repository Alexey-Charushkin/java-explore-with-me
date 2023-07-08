package ru.practicum.main.comment.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.comment.dao.CommentRepository;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.model.CommentState;
import ru.practicum.main.event.dao.EventRepository;
import ru.practicum.main.event.dto.State;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.exception.BadRequestException;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.user.dao.UserRepository;
import ru.practicum.main.user.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Log4j2
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentDto save(NewCommentDto newCommentDto) {
        User user = userRepository.findById(newCommentDto.getAuthorId())
                .orElseThrow(() -> new ConflictException("User not found"));
        Event event = eventRepository.findById(newCommentDto.getEventId())
                .orElseThrow(() -> new ConflictException("Event not found"));
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Можно добавить комментарий только к опубликованному событию");
        }
        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        comment.setUser(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now());
        comment.setCommentState(CommentState.WAITING);

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto patchByUser(Integer userId, Integer commentId, NewCommentDto commentRequestDto) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ConflictException("Comment not found"));
        comment.setText(commentRequestDto.getText());
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto patchByAdmin(Integer commentId, String state) {
        if (state == null) {
            throw new BadRequestException("State is null");
        }
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ConflictException("Comment not found"));
        if (comment.getCommentState().equals(CommentState.WAITING)) {
            comment.setCommentState(CommentState.valueOf(state));
            commentRepository.save(comment);
        } else {
            throw new ConflictException("Comment state is not Waiting");
        }
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto delete(Integer userId, Integer commentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ConflictException("Comment not found"));
        if (!comment.getUser().getId().equals(userId)) {
            throw new ConflictException("User is not author of comment");
        }
        if (!comment.getCommentState().equals(CommentState.WAITING)) {
            throw new ConflictException("Comment state is not Waiting");
        }
        commentRepository.deleteById(commentId);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    public CommentDto getById(Integer commentId) {
        return CommentMapper.toCommentDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException("Comment not found")));
    }

    @Override
    public CommentDto getByUserIdAndCommentId(Integer userId, Integer eventId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found"));
        eventRepository.findById(eventId)
                .orElseThrow(() -> new ConflictException("Event not found"));
        Optional<Comment> comment = Optional.of(commentRepository.findByUserIdAndEventId(userId, eventId));
        if (!comment.isPresent()) {
            throw new ConflictException("Comment not found");
        }
        if (!comment.get().getUser().getId().equals(userId)) {
            throw new ConflictException("User is not author of comment");
        }
        if (!comment.get().getCommentState().equals(CommentState.WAITING)) {
            throw new ConflictException("Comment state is not Waiting");
        }
        return CommentMapper.toCommentDto(comment.get());
    }

    @Override
    public List<CommentDto> findAllByUserIdAndRequestParam(Integer userId, String state, String start, String end,
                                                           Integer from, Integer size) {
        List<Comment> comments;
        Pageable pageable = PageRequest.of(from, size);
        userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found"));
        if (state == null) {
            if (start == null & end == null) {
                comments = commentRepository.findAllByUserIdAndCreatedIsAfter(
                        userId, LocalDateTime.now(), pageable);
            } else {
                comments = commentRepository.findAllByUserIdAndCreatedIsAfterAndCreatedIsBefore(
                        userId, LocalDateTime.parse(start, formatter),
                        LocalDateTime.parse(end, formatter), pageable);
            }
        } else {
            if (start == null & end == null) {
                comments = commentRepository.findAllByUserIdAndCommentStateAndCreatedIsAfter(
                        userId, CommentState.valueOf(state), LocalDateTime.now(), pageable);
            } else {
                comments = commentRepository.findAllByUserIdAndCommentStateAndCreatedIsAfterAndCreatedIsBefore(
                        userId, CommentState.valueOf(state), LocalDateTime.parse(start, formatter),
                        LocalDateTime.parse(end, formatter), pageable);
            }
        }
        return CommentMapper.toCommentDtoList(comments);
    }

    @Override
    public List<CommentDto> findAllByEventIdAndRequestParam(Integer userId, String state, String start, String end, Integer from, Integer size) {
        List<Comment> comments;
        Pageable pageable = PageRequest.of(from, size);
        userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found"));
        if (state == null) {
            if (start == null & end == null) {
                comments = commentRepository.findAllByEventIdAndCreatedIsAfter(
                        userId, LocalDateTime.now(), pageable);
            } else {
                comments = commentRepository.findAllByEventIdAndCreatedIsAfterAndCreatedIsBefore(
                        userId, LocalDateTime.parse(start, formatter),
                        LocalDateTime.parse(end, formatter), pageable);
            }
        } else {
            if (start == null & end == null) {
                comments = commentRepository.findAllByEventIdAndCommentStateAndCreatedIsAfter(
                        userId, CommentState.valueOf(state), LocalDateTime.now(), pageable);
            } else {
                comments = commentRepository.findAllByEventIdAndCommentStateAndCreatedIsAfterAndCreatedIsBefore(
                        userId, CommentState.valueOf(state), LocalDateTime.parse(start, formatter),
                        LocalDateTime.parse(end, formatter), pageable);
            }
        }
        return CommentMapper.toCommentDtoList(comments);
    }

    @Override
    public List<CommentDto> findAllByUserIdsAndRequestParam(Integer[] ids, String state, String start, String end,
                                                            Integer from, Integer size) {
        List<Comment> comments;
        Pageable pageable = PageRequest.of(from, size);
        if (ids != null) {
            if (state == null) {
                if (start == null & end == null) {
                    comments = commentRepository.findAllByUserIdInAndCreatedIsAfter(
                            ids, LocalDateTime.now().minusDays(7), pageable);
                } else {
                    comments = commentRepository.findAllByUserIdInAndCreatedIsAfterAndCreatedIsBefore(
                            ids, LocalDateTime.parse(start, formatter),
                            LocalDateTime.parse(end, formatter), pageable);
                }
            } else {
                if (start == null & end == null) {
                    comments = commentRepository.findAllByUserIdInAndCommentStateAndCreatedIsAfter(
                            ids, CommentState.valueOf(state), LocalDateTime.now().minusDays(7), pageable);
                } else {
                    comments = commentRepository.findAllByUserIdInAndCommentStateAndCreatedIsAfterAndCreatedIsBefore(
                            ids, CommentState.valueOf(state), LocalDateTime.parse(start, formatter),
                            LocalDateTime.parse(end, formatter), pageable);
                }
            }
        } else {
            comments = commentRepository.findAll();
        }
        return CommentMapper.toCommentDtoList(comments);
    }

    @Override
    public List<CommentDto> findAllByEventIdsAndRequestParam(Integer[] ids, String state, String start, String end, Integer from, Integer size) {
        List<Comment> comments;
        Pageable pageable = PageRequest.of(from, size);
        if (ids != null) {
            if (state == null) {
                if (start == null & end == null) {
                    comments = commentRepository.findAllByEventIdInAndCreatedIsAfter(
                            ids, LocalDateTime.now().minusDays(7), pageable);
                } else {
                    comments = commentRepository.findAllByEventIdInAndCreatedIsAfterAndCreatedIsAfter(
                            ids, LocalDateTime.parse(start, formatter),
                            LocalDateTime.parse(end, formatter), pageable);
                }
            } else {
                if (start == null & end == null) {
                    comments = commentRepository.findAllByEventIdInAndCommentStateAndCreatedIsAfter(
                            ids, CommentState.valueOf(state), LocalDateTime.now().minusDays(7), pageable);
                } else {
                    comments = commentRepository.findAllByEventIdInAndCommentStateAndCreatedIsAfterAndCreatedIsBefore(
                            ids, CommentState.valueOf(state), LocalDateTime.parse(start, formatter),
                            LocalDateTime.parse(end, formatter), pageable);
                }
            }
        } else {
            comments = commentRepository.findAll();
        }
        return CommentMapper.toCommentDtoList(comments);
    }
}
