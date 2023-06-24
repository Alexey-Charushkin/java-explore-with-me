package ru.practicum.main.locations.model;

import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "locations", schema = "public")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "lat", nullable = false)
    private float lat; // example: 55.754167 Широта
    @Column(name = "lon", nullable = false)
    private float lon; // example: 37.6 Долгота
}
