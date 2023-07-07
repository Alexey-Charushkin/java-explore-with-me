package ru.practicum.server.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@RestControllerAdvice
public class ErrorHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.debug("Получен статус 404 Not found {}", e.getMessage(), e);
        return new ApiError(e.getStackTrace(), e.getMessage(), "NotFoundException", HttpStatus.NOT_FOUND,
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleAvailableBadRequestException(final ConstraintViolationException e) {
        log.debug("Получен статус 400 Bad Request {}", e.getMessage(), e);
        return new ApiError(e.getStackTrace(), e.getMessage(), "ConstraintViolationException", HttpStatus.BAD_REQUEST,
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleAvailableBadRequestException(final BadRequestException e) {
        log.debug("Получен статус 400 Bad Request {}", e.getMessage(), e);
        return new ApiError(e.getStackTrace(), e.getMessage(), "BadRequestException", HttpStatus.BAD_REQUEST,
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleAvailableMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.debug("Получен статус 400 Bad Request {}", e.getMessage(), e);
        return new ApiError(e.getStackTrace(), e.getMessage(), "MethodArgumentNotValid", HttpStatus.BAD_REQUEST,
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleAvailableMethodConflictException(final ConflictException e) {
        log.debug("Получен статус 409 Bad Conflict {}", e.getMessage(), e);
        return new ApiError(e.getStackTrace(), e.getMessage(), "Conflict with existing data", HttpStatus.CONFLICT,
                LocalDateTime.now().format(formatter));
    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ApiError handleAvailableInternalServerErrorException(final Throwable e) {
//        log.debug("Получен статус 500 Internal Server Error {}", e.getMessage(), e);
//        return new ApiError(e.getStackTrace(), e.getMessage(), "Server Error", HttpStatus.INTERNAL_SERVER_ERROR,
//                LocalDateTime.now().format(formatter));
//    }
}
