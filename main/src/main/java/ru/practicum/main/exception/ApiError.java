package ru.practicum.main.exception;

import java.util.List;

public class ApiError {
    List<String> errors; // example: List []. Список стектрейсов или описания ошибок
    String message; // example: Only pending or canceled events can be changed. Сообщение об ошибке
    String reason; // example: For the requested operation the conditions are not met. Общее описание причины ошибки
    Enum status; // example: FORBIDDEN. Код статуса HTTP-ответа
    String timestamp; // example: 2022-06-09 06:27:23 Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
}
