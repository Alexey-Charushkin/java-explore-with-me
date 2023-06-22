package ru.practicum.main.request.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCompilationRequest {
    // Изменение информации о подборке событий. Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
    private List<Integer> events; // uniqueItems: true  Список id событий подборки для полной замены текущего списка
    private boolean pinned; // Закреплена ли подборка на главной странице сайта
    @NotBlank
    @Size(min = 1, max = 50)
    private String title; // example: Необычные фотозоны Заголовок подборки
}
