package ru.tinkoff.academy.events.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventService {
    private final OrderEventRepository orderEventRepository;


}
