package client;


import dto.StatsDtoToGetStats;
import dto.StatsDtoToReturn;
import dto.StatsDtoToSave;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@Log4j2
class StatisticsWebClient {
    WebClient webClient = WebClient.create();

    public void saveHit(String uri, StatsDtoToSave statsDtoToSave) {
        webClient
                .method(HttpMethod.POST)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(statsDtoToSave))
                .retrieve()
                .toEntity(String.class)
                .subscribe(responseEntity -> {
                    System.out.println("Статус код: " + responseEntity.getStatusCode());
                    System.out.println("Ответ: " + responseEntity.getBody());
                });
    }

    public List<StatsDtoToReturn> getStatistics(String baseUrl, StatsDtoToGetStats statsParameters) {
        String endpointPath = "/stats";

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl + endpointPath)
                .queryParam("start", statsParameters.getStart())
                .queryParam("end", statsParameters.getEnd())
                .queryParam("uris", statsParameters.getUris())
                .queryParam("unique", statsParameters.isUnique())
                .queryParam("from", statsParameters.getFrom())
                .queryParam("size", statsParameters.getSize())
                .toUriString();

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(StatsDtoToReturn.class)
                .collectList()
                .block();
    }
}