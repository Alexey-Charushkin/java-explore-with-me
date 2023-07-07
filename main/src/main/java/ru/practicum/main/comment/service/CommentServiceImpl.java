package ru.practicum.main.comment.service;

import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommentServiceImpl {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDto addComment(Long userId, CommentRequestDto commentRequestDto) throws InvalidParameterException, ConflictException {
        log.info("Call#CommentServiceImpl#addComment# userId: {}, commentRequestDto: {}", userId, commentRequestDto);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new InvalidParameterException("Нет пользователя с id: " + userId);
        }
        Optional<Event> event = eventRepository.findById(commentRequestDto.getEventId());
        if (event.isEmpty()) {
            throw new InvalidParameterException("Нет события с id: " + commentRequestDto.getEventId());
        }
        if (!event.get().getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Можно добавить комментарий только к опубликованному событию");
        }
        Comment comment = new Comment();
        comment.setText(commentRequestDto.getText());
        comment.setUser(user.get());
        comment.setEvent(event.get());
        comment.setCreated(LocalDateTime.now());
        comment.setCommentState(CommentState.WAITING);

        return CommentMapper.toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public CommentResponseDto updateComment(Long userId, Long commentId, CommentRequestDto commentRequestDto) throws InvalidParameterException {
        log.info("Call#CommentServiceImpl#updateComment# userId: {}, commentRequestDto: {}", userId, commentRequestDto);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new InvalidParameterException("Нет пользователя с id: " + commentRequestDto.getEventId());
        }
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new InvalidParameterException("Нет комментария с id: " + commentId);
        }
        comment.get().setText(commentRequestDto.getText());
        return CommentMapper.toCommentResponseDto(commentRepository.save(comment.get()));
    }

    @Override
    public CommentResponseDto deleteComment(Long userId, Long commentId) throws InvalidParameterException, ConflictException {
        log.info("Call#CommentServiceImpl#addComment# userId: {}, commentId: {}", userId, commentId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new InvalidParameterException("Нет пользователя с id: " + userId);
        }
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new InvalidParameterException("Нет комментария с id: " + commentId);
        }
        if (!comment.get().getCommentState().equals(CommentState.WAITING)) {
            throw new ConflictException("Удалить комменарий можно только в состоянии WAITING");
        }
        commentRepository.deleteById(commentId);
        return CommentMapper.toCommentResponseDto(comment.get());
    }

    @Override
    public CommentResponseDto getComment(Long userId, Long commentId) throws InvalidParameterException {
        log.info("Call#CommentServiceImpl#deleteComment# userId: {}, commentId: {}", userId, commentId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new InvalidParameterException("Нет пользователя с id: " + userId);
        }
        return CommentMapper.toCommentResponseDto(commentRepository.getById(commentId));
    }

    @Override
    public List<CommentResponseDto> getComments(Long eventId, String text, LocalDateTime rangeStart, LocalDateTime rangeEnd, String sort, Integer from, Integer size) throws InvalidParameterException {
        log.info("Call#CommentServiceImpl#getComments# userId: {}, text: {}", eventId, text);

        if (sort != null && !"ASC".equalsIgnoreCase(sort) && !"DESC".equalsIgnoreCase(sort)) {
            throw new InvalidParameterException("Параметр sort может принимать или ASC или DESC");
        }
        PageRequest pageable = PageRequest.of(from / size, size);

        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new InvalidParameterException("Время начала интервала не должно быть позже времени окончания интервала");
            }
        }

        return commentRepository.getComments(eventId, text, rangeStart, rangeEnd, sort, CommentState.PUBLISHED, pageable).stream()
                .map(c -> CommentMapper.toCommentResponseDto(c)).collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto banComment(Long userId, Long commentId) throws EntityNotFoundException, ConflictException {
        log.info("Call#CommentServiceImpl#getComments# userId: {}, commentId: {}", userId, commentId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new EntityNotFoundException("Нет комментария с id: " + commentId);
        }
        if (comment.get().getCommentState().equals(CommentState.BANNED) || comment.get().getCommentState().equals(CommentState.PUBLISHED)) {
            throw new ConflictException("Забанить комментарий можно только в состоянии WAITING");
        }

        comment.get().setCommentState(CommentState.BANNED);
        return CommentMapper.toCommentResponseDto(commentRepository.save(comment.get()));
    }

    @Override
    public CommentResponseDto publishComment(Long userId, Long commentId) throws EntityNotFoundException, ConflictException {
        log.info("Call#CommentServiceImpl#publishComment# userId: {}, commentId: {}", userId, commentId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) {
            throw new EntityNotFoundException("Нет комментария с id: " + commentId);
        }
        if (comment.get().getCommentState().equals(CommentState.BANNED) || comment.get().getCommentState().equals(CommentState.PUBLISHED)) {
            throw new ConflictException("Опубликовать комментарий можно только в состоянии WAITING");
        }

        comment.get().setCommentState(CommentState.PUBLISHED);
        return CommentMapper.toCommentResponseDto(commentRepository.save(comment.get()));
    }

    @Override
    public List<CommentResponseDto> getCommentsByAdmin(Long userId, Long eventId, String text,
                                                       LocalDateTime rangeStart, LocalDateTime rangeEnd, String sort, Integer from, Integer size) throws InvalidParameterException {
        log.info("Call#CommentServiceImpl#getCommentsByAdmin# userId: {}, eventId: {}, text: {}, rangeStart: {}, " +
                "rangeEnd: {}, sort: {}, from: {}, size: {}", userId, eventId, text, rangeStart, rangeEnd, sort, from, size);
        if (sort != null && !"ASC".equalsIgnoreCase(sort) && !"DESC".equalsIgnoreCase(sort)) {
            throw new InvalidParameterException("Параметр sort может принимать или ASC или DESC");
        }
        PageRequest pageable = PageRequest.of(from / size, size);

        if (rangeStart != null && rangeEnd != null) {
            if (rangeEnd.isBefore(rangeStart)) {
                throw new InvalidParameterException("Время начала интервала не должно быть позже времени окончания интервала");
            }
        }

        return commentRepository.getCommentsByAdmin(userId, eventId, text, rangeStart, rangeEnd, sort, CommentState.PUBLISHED, pageable).stream()
                .map(c -> CommentMapper.toCommentResponseDto(c)).collect(Collectors.toList());
    }
}
