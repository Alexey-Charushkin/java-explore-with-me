package ru.practicum.main.user.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private float lat; // example: 55.754167 Широта

    private float lon; // example: 37.6 Долгота
}
