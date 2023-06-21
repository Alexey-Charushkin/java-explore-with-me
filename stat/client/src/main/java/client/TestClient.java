package client;

import dto.StatsDtoToGetStats;
import dto.StatsDtoToReturn;
import dto.StatsDtoToSave;

import java.util.List;

class TestClient {
    public static void main(String[] args) {

        StatisticsWebClient client = new StatisticsWebClient();

        StatsDtoToSave statsDtoToSave = new StatsDtoToSave("test client", "/test/client", "192.163.0.1",
                "2022-09-06 11:00:23");

        String baseUrl = "http://localhost:9090";

        String[] urisParam = new String[]{"/events", "/events/1", "/events/2"};

        client.saveHit(baseUrl + "/hit", statsDtoToSave);

        StatsDtoToGetStats stats = new StatsDtoToGetStats("2020-05-05 00:00:00", "2035-05-05 00:00:00",
                urisParam, true, null, null);

        List<StatsDtoToReturn> statistics = client.getStatistics(baseUrl, stats);
        System.out.println();
        System.out.println("Список Dto: " + statistics);
        System.out.println();
    }
}