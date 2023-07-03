package ru.practicum.server.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ApiError {
    List<String> errors;
    String message;
    String reason;
    String status;
    String timestamp;

    public ApiError(StackTraceElement[] stackTrace, String message, String reason, HttpStatus status, String timestamp) {
        this.errors = Arrays.stream(stackTrace).map(s -> s.toString()).collect(Collectors.toList());
        this.message = message;
        this.reason = reason;
        this.status = status.toString();
        this.timestamp = timestamp;
    }
}
