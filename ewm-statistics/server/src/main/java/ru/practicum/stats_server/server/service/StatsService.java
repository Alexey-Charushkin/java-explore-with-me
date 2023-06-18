package ru.practicum.stats_server.server.service;

import dto.StatsDtoToReturn;
import dto.StatsDtoToSave;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StatsService {
    void save(StatsDtoToSave statsDtoToSave);

    List<StatsDtoToReturn> get(LocalDateTime start, LocalDateTime end, String[] iris, boolean unique, int from, int size);
}
