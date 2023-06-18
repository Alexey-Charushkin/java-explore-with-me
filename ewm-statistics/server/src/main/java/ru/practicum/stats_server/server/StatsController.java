package ru.practicum.stats_server.server;


import dto.StatsDtoToReturn;
import dto.StatsDtoToSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats_server.server.service.StatsService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class StatsController {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<String> create(@RequestBody StatsDtoToSave statsDtoToSave) {
        log.info("/hit");
        statsService.save(statsDtoToSave);

        return ResponseEntity.status(HttpStatus.CREATED).body("Информация сохранена");
    }

    @GetMapping("/stats")
    public List<StatsDtoToReturn> get(@RequestParam(name = "start") String start,
                                      @RequestParam(name = "end") String end,
                                      @RequestParam(name = "uris", required = false) String[] uris,
                                      @RequestParam(name = "unique", defaultValue = "false") boolean unique,
                                      @RequestParam(name = "from", defaultValue = "0") Integer from,
                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {

        log.info("/stats");

        return statsService.get(LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), formatter),
                LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), formatter), uris,
                unique, from, size);
    }
}