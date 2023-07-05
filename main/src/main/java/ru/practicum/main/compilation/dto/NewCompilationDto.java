package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.main.compilation.controller.Create;
import ru.practicum.main.compilation.controller.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    private List<Integer> events;
    private boolean pinned;
    @NotNull(groups = {Create.class})
    @NotBlank(groups = {Create.class})
    @Size(min = 1, max = 50, groups = {Create.class, Update.class})
    private String title;
}
