package ru.tinkoff.academy.landscape;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientHelper {
    @Value("${rest.landscape.url}")
    private String url;

    public WebClient webClient() {
        return WebClient.create(this.url);
    }
}
