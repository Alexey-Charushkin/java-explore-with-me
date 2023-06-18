package ru.practicum.stats_server.server.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats_server.server.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stats, Integer> {

    List<Stats> findByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris, Pageable page);

    List<Stats> findByTimestampBetween(LocalDateTime start, LocalDateTime end, Pageable page);

}

