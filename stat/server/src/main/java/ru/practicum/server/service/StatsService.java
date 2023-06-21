package ru.practicum.server.service;

import dto.StatsDtoToReturn;
import dto.StatsDtoToSave;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void save(StatsDtoToSave statsDtoToSave);

    List<StatsDtoToReturn> get(LocalDateTime start, LocalDateTime end, String[] iris, boolean unique, int from, int size);
}
