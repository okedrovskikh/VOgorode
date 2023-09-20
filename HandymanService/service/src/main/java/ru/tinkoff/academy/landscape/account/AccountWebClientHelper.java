package ru.tinkoff.academy.landscape.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.landscape.account.Account;
import ru.tinkoff.academy.landscape.account.dto.AccountCreateDto;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountWebClientHelper {
    private final WebClient landscapeWebClient;

    public WebClient webClient() {
        return landscapeWebClient;
    }

    public Mono<Account> saveUser(AccountCreateDto userCreateDto) {
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
                .bodyToMono(Account.class);
    }

    public Mono<Account> getUser(UUID userID) {
        return landscapeWebClient.get()
                .uri(String.format("/users/%s", userID))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Account.class);
    }
}
