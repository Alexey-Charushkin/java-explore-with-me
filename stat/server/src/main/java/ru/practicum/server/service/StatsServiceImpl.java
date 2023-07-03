package ru.practicum.server.service;

import dto.StatsDtoToReturn;
import dto.StatsDtoToSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.server.exception.BadRequestException;
import ru.practicum.server.mapper.StatsMapper;
import ru.practicum.server.model.Stats;
import ru.practicum.server.dao.StatsRepository;

import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    Comparator<Stats> comparator = (o1, o2) -> o2.getHits().compareTo(o1.getHits());

    @Override
    public void save(StatsDtoToSave statsDtoToSave) {
        statsRepository.save(StatsMapper.toStats(statsDtoToSave));
    }

    @Override
    public List<StatsDtoToReturn> get(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique, int from,
                                      int size) {

        if (end.isBefore(start)) {
            throw new BadRequestException("Start before end");
        }
        Pageable page = PageRequest.of(from, size);
        List<Stats> statsList;

        if (uris == null || uris.length == 0) {
            statsList = statsRepository.findByTimestampBetween(start, end, page);
        } else {
            List<String> urisList = Arrays.asList(uris);
            statsList = statsRepository.findByTimestampBetweenAndUriIn(start, end, urisList, page);
        }

        List<Stats> result = getHits(statsList, unique);

        if (result.size() > 1) result.sort(comparator);

        return StatsMapper.toStatsDtoToReturnList(result);
    }

    private List<Stats> getHits(List<Stats> statsList, boolean unique) {
        Stats oldStats;
        Map<String, Stats> statsMap = new HashMap<>();
        for (Stats stats : statsList) {
            if (!statsMap.containsKey(stats.getUri() + stats.getIp())) {
                stats.setHits(1);
                statsMap.put(stats.getUri() + stats.getIp(), stats);
            } else {
                oldStats = statsMap.get(stats.getUri() + stats.getIp());
                if (unique) {
                    if (!oldStats.getIp().equals(stats.getIp())) {
                        oldStats.setHits(oldStats.getHits() + 1);
                        statsMap.put(stats.getUri() + stats.getIp(), oldStats);
                    }
                } else {
                    oldStats.setHits(oldStats.getHits() + 1);
                    statsMap.put(stats.getUri() + stats.getIp(), oldStats);
                }
            }
        }
        return new ArrayList<>(statsMap.values());
    }
}