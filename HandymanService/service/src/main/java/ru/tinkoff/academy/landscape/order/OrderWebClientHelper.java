package ru.tinkoff.academy.landscape.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.landscape.order.dto.WorkerUpdateDto;

@Component
@RequiredArgsConstructor
public class OrderWebClientHelper {
    private final WebClient landscapeWebClient;

    public Mono<Order> getOrderById(Long id) {
        return landscapeWebClient.get()
                .uri(String.format("/orders/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> HttpStatus.INTERNAL_SERVER_ERROR == status, response -> {
                    throw new IllegalStateException("Service unavailable");
                })
                .bodyToMono(Order.class);
    }

    public Mono<Order> updateOrderStatus(WorkerUpdateDto request) {
        return landscapeWebClient.put()
                .uri("/orders/status")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> HttpStatus.CONFLICT == status, response -> {
                    throw new IllegalStateException("Invalid request");
                })
                .onStatus(status -> HttpStatus.INTERNAL_SERVER_ERROR == status, response -> {
                    throw new IllegalStateException("Service unavailable");
                })
                .bodyToMono(Order.class);
    }
}
