package ru.practicum.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank
    @Email
    private String email;
    private Long id;
    @NotBlank
    private String name;
}
