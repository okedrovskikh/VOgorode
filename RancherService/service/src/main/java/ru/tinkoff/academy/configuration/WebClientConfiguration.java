package ru.tinkoff.academy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {
    @Bean
    public WebClient landscapeWebClient(@Value("${rest.landscape.url}") String url) {
        return WebClient.builder()
                .baseUrl(url)
                .build();
    }
}
