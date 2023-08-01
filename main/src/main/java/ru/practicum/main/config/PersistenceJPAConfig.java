package ru.practicum.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.practicum.client.StatisticsWebClient;


@Configuration
@EnableJpaRepositories(basePackages = "ru.practicum.main")
class PersistenceJPAConfig {
    @Bean
    public StatisticsWebClient statisticsWebClient() {
        return new StatisticsWebClient();
    }
}
