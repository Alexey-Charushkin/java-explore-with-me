package ru.practicum.main.category.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public
class CategoryDto {
    private Integer id;
    @NotBlank
    @Size(min = 1, max = 50)
    private String name; // maxLength: 50 minLength: 1 example: Концерты Название категории
}
