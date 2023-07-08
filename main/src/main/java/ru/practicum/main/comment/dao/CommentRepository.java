package ru.practicum.main.comment.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.model.CommentState;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Comment findByUserIdAndEventId(Integer userId, Integer eventId);

    List<Comment> findAllByUserIdAndCommentStateAndCreatedIsAfterAndCreatedIsBefore(
            Integer userId, CommentState state, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Comment> findAllByUserIdInAndCommentStateAndCreatedIsAfterAndCreatedIsBefore(
            Integer[] userIds, CommentState state, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Comment> findAllByEventIdInAndCommentStateAndCreatedIsAfterAndCreatedIsBefore(
            Integer[] userIds, CommentState state, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Comment> findAllByEventIdAndCommentStateAndCreatedIsAfterAndCreatedIsBefore(
            Integer userId, CommentState state, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Comment> findAllByUserIdAndCreatedIsAfterAndCreatedIsBefore(
            Integer userId, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Comment> findAllByUserIdInAndCreatedIsAfterAndCreatedIsBefore(
            Integer[] userIds, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Comment> findAllByEventIdInAndCreatedIsAfterAndCreatedIsAfter(
            Integer[] userIds, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Comment> findAllByEventIdAndCreatedIsAfterAndCreatedIsBefore(
            Integer userId, LocalDateTime start, LocalDateTime end, Pageable pageable
    );

    List<Comment> findAllByUserIdAndCreatedIsAfter(
            Integer userId, LocalDateTime localDateTime, Pageable pageable
    );

    List<Comment> findAllByUserIdInAndCreatedIsAfter(
            Integer[] userIds, LocalDateTime localDateTime, Pageable pageable
    );

    List<Comment> findAllByEventIdInAndCreatedIsAfter(
            Integer[] userIds, LocalDateTime localDateTime, Pageable pageable
    );

    List<Comment> findAllByEventIdAndCreatedIsAfter(
            Integer userId, LocalDateTime localDateTime, Pageable pageable
    );

    List<Comment> findAllByUserIdAndCommentStateAndCreatedIsAfter(
            Integer userId, CommentState state, LocalDateTime localDateTime, Pageable pageable
    );

    List<Comment> findAllByUserIdInAndCommentStateAndCreatedIsAfter(
            Integer[] userIds, CommentState state, LocalDateTime localDateTime, Pageable pageable
    );

    List<Comment> findAllByEventIdInAndCommentStateAndCreatedIsAfter(
            Integer[] userIds, CommentState state, LocalDateTime localDateTime, Pageable pageable
    );

    List<Comment> findAllByEventIdAndCommentStateAndCreatedIsAfter(
            Integer userId, CommentState state, LocalDateTime localDateTime, Pageable pageable
    );


//    findAllByUserIdAndRequestParam(Integer userId, String state, String start, String end,
//                                   Integer from, Integer size)

//    @Query("SELECT c " +
//            "FROM Comment AS c " +
//            "WHERE " +
//            ":text IS NULL OR LOWER(c.text) LIKE LOWER(CONCAT('%', :text, '%')) " +
//            "AND (:eventId IS NULL OR c.event.id = :eventId) " +
//            "AND (CAST(:rangeStart AS date) IS NULL OR c.created >= :rangeStart) " +
//            "AND (CAST(:rangeEnd AS date) IS NULL OR c.created <= :rangeEnd) " +
//            "AND c.commentState = :commentState " +
//            "ORDER BY :sort")
//    List<Comment> getComments(@Param("eventId") Long eventId,
//                              @Param("text") String text,
//                              @Param("rangeStart") LocalDateTime rangeStart,
//                              @Param("rangeEnd") LocalDateTime rangeEnd,
//                              @Param("sort") String sort,
//                              @Param("commentState") CommentState commentState,
//                              Pageable pageable);
//
//
//    @Query("SELECT c " +
//            "FROM Comment AS c " +
//            "WHERE " +
//            "c.user.id = :userId " +
//            "AND (:text IS NULL OR LOWER(c.text) LIKE LOWER(CONCAT('%', :text, '%'))) " +
//            "AND (:eventId IS NULL OR c.event.id = :eventId) " +
//            "AND (CAST(:rangeStart AS date) IS NULL OR c.created >= :rangeStart) " +
//            "AND (CAST(:rangeEnd AS date) IS NULL OR c.created <= :rangeEnd) " +
//            "AND c.commentState = :commentState " +
//            "ORDER BY :sort")
//    List<Comment> getCommentsByAdmin(@Param("userId") Long userId,
//                                     @Param("eventId") Long eventId,
//                                     @Param("text") String text,
//                                     @Param("rangeStart") LocalDateTime startTime,
//                                     @Param("rangeEnd") LocalDateTime endTime,
//                                     @Param("sort") String sort,
//                                     @Param("commentState") CommentState commentState,
//                                     PageRequest pageable);
//}
}
