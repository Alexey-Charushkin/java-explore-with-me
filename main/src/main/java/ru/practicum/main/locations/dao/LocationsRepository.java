package ru.practicum.main.locations.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.locations.model.Location;

public interface LocationsRepository extends JpaRepository<Location, Integer> {

}
