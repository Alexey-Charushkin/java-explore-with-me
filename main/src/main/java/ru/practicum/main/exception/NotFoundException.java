package ru.practicum.main.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

//    public NotFoundException(ApiError apiError) {
//        apiError.getErrors(),
//                apiError.getMessage(),
//                apiError.getReason(),
//                apiError.getStatus(),
//                apiError.getTimestamp()
//    }
}
    ;

