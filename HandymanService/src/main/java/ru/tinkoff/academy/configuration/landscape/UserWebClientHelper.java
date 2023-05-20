package ru.tinkoff.academy.configuration.landscape;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.landscape.user.LandscapeUser;
import ru.tinkoff.academy.landscape.user.dto.LandscapeUserCreateDto;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserWebClientHelper {
    private final WebClient landscapeWebClient;

    public WebClient webClient() {
        return landscapeWebClient;
    }

    public Mono<LandscapeUser> saveUser(LandscapeUserCreateDto userCreateDto) {
        return landscapeWebClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userCreateDto)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> HttpStatus.CONFLICT == status, response -> {
                    throw new IllegalStateException("Invalid request");
                })
                .onStatus(status -> HttpStatus.INTERNAL_SERVER_ERROR == status, response -> {
                    throw new IllegalStateException("Service unavailable");
                })
                .bodyToMono(LandscapeUser.class);
    }

    public Mono<LandscapeUser> getUser(UUID userID) {
        return landscapeWebClient.get()
                .uri(String.format("/users/%s", userID))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(LandscapeUser.class);
    }
}
