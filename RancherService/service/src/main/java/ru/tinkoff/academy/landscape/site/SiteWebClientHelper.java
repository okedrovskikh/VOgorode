package ru.tinkoff.academy.landscape.site;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.landscape.site.Site;
import ru.tinkoff.academy.landscape.site.dto.SiteCreateDto;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SiteWebClientHelper {
    private final WebClient landscapeWebClient;

    public Mono<Site> saveSite(SiteCreateDto siteCreateDto) {
        return landscapeWebClient.post()
                .uri("/sites")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(siteCreateDto)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> HttpStatus.CONFLICT == status, response -> {
                    throw new IllegalStateException("Invalid request");
                })
                .onStatus(status -> HttpStatus.INTERNAL_SERVER_ERROR == status, response -> {
                    throw new IllegalStateException("Service unavailable");
                })
                .bodyToMono(Site.class);
    }

    public Mono<Site> getSiteById(UUID id) {
        return landscapeWebClient.get()
                .uri(String.format("/sites/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> HttpStatus.CONFLICT == status, response -> {
                    throw new IllegalStateException("Invalid request");
                })
                .onStatus(status -> HttpStatus.INTERNAL_SERVER_ERROR == status, response -> {
                    throw new IllegalStateException("Service unavailable");
                })
                .bodyToMono(Site.class);
    }
}
