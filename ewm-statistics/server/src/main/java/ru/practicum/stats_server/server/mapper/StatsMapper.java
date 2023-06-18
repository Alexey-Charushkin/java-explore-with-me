package ru.practicum.stats_server.server.mapper;

import dto.StatsDtoToReturn;
import dto.StatsDtoToSave;
import lombok.experimental.UtilityClass;
import ru.practicum.stats_server.server.model.Stats;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public
class StatsMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Stats toStats(StatsDtoToSave statsDtoToSave) {
        return new Stats(
                statsDtoToSave.getApp(),
                statsDtoToSave.getUri(),
                statsDtoToSave.getIp(),
                LocalDateTime.parse(statsDtoToSave.getTimestamp(), formatter),
                null
        );
    }

    public StatsDtoToReturn toStatsDtoToReturn(Stats stats) {
        return new StatsDtoToReturn(
                stats.getApp(),
                stats.getUri(),
                stats.getHits()
        );
    }

    public List<StatsDtoToReturn> toStatsDtoToReturnList(List<Stats> statsList) {
        return statsList.stream()
                .map(StatsMapper::toStatsDtoToReturn)
                .collect(Collectors.toList());
    }
}
