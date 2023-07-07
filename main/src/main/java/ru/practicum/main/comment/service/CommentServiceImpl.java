package ru.practicum.main.comment.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
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

import javax.persistence.EntityNotFoundException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Getter
@Setter
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentDto save(NewCommentDto commentRequestDto) {
        User user = userRepository.findById(commentRequestDto.getAuthorId())
                .orElseThrow(() -> new ConflictException("User not found"));
        Event event = eventRepository.findById(commentRequestDto.getEventId())
                .orElseThrow(() -> new ConflictException("Event not found"));
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Можно добавить комментарий только к опубликованному событию");
        }
        Comment comment = new Comment();
        comment.setText(commentRequestDto.getText());
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
    public CommentDto delete(Integer userId, Integer commentId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ConflictException("User not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ConflictException("Comment not found"));
        if(!comment.getUser().getId().equals(userId)) {
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
//
//    @Override
//    public List<CommentDto> getAllByEventId(Integer eventId, String text, LocalDateTime start,
//                                            LocalDateTime end, String sort, Integer from, Integer size) {
//
//        if (sort != null && !"ASC".equalsIgnoreCase(sort) && !"DESC".equalsIgnoreCase(sort)) {
//            throw new InvalidParameterException("Параметр sort может принимать или ASC или DESC");
//        }
//        PageRequest pageable = PageRequest.of(from / size, size);
//
//        if (start != null && end != null) {
//            if (end.isBefore(start)) {
//                throw new InvalidParameterException("Время начала интервала не должно быть позже времени окончания интервала");
//            }
//        }
//
//        return commentRepository.getComments(eventId, text, start, end, sort, CommentState.PUBLISHED,
//                        pageable).stream()
//                .map(c -> CommentMapper.toCommentDto(c)).collect(Collectors.toList());
//    }
//
//    @Override
//    public CommentDto patchByAdmin(Integer userId, Integer commentId) {
//        Optional<Comment> comment = commentRepository.findById(commentId);
//        if (comment.isEmpty()) {
//            throw new EntityNotFoundException("Нет комментария с id: " + commentId);
//        }
//        if (comment.get().getCommentState().equals(CommentState.REJECTED)
//                || comment.get().getCommentState().equals(CommentState.PUBLISHED)) {
//            throw new ConflictException("Забанить комментарий можно только в состоянии WAITING");
//        }
//
//        comment.get().setCommentState(CommentState.REJECTED);
//        return CommentMapper.toCommentDto(commentRepository.save(comment.get()));
//    }
//
//    @Override
//    public CommentDto publishComment(Integer userId, Integer commentId) {
//
//        Optional<Comment> comment = commentRepository.findById(commentId);
//        if (comment.isEmpty()) {
//            throw new EntityNotFoundException("Нет комментария с id: " + commentId);
//        }
//        if (comment.get().getCommentState().equals(CommentState.REJECTED)
//                || comment.get().getCommentState().equals(CommentState.PUBLISHED)) {
//            throw new ConflictException("Опубликовать комментарий можно только в состоянии WAITING");
//        }
//
//        comment.get().setCommentState(CommentState.PUBLISHED);
//        return CommentMapper.toCommentDto(commentRepository.save(comment.get()));
//    }
//
//    @Override
//    public List<CommentDto> getCommentsByAdmin(Integer userId, Integer eventId, String text, LocalDateTime rangeStart,
//                                               LocalDateTime rangeEnd, String sort, Integer from, Integer size) {
//        if (sort != null && !"ASC".equalsIgnoreCase(sort) && !"DESC".equalsIgnoreCase(sort)) {
//            throw new InvalidParameterException("Параметр sort может принимать или ASC или DESC");
//        }
//        PageRequest pageable = PageRequest.of(from, size);
//
//        if (rangeStart != null && rangeEnd != null) {
//            if (rangeEnd.isBefore(rangeStart)) {
//                throw new InvalidParameterException("Время начала интервала не должно быть позже времени окончания интервала");
//            }
//        }
//
//        return commentRepository.getCommentsByAdmin(userId, eventId, text, rangeStart, rangeEnd, sort,
//                        CommentState.PUBLISHED, pageable).stream()
//                .map(c -> CommentMapper.toCommentDto(c)).collect(Collectors.toList());
//    }
}
