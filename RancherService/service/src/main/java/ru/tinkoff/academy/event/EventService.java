package ru.tinkoff.academy.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.landscape.order.Order;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event save(Order order) {
        return eventRepository.save(Event.builder().orderId(order.getId()).status(EventStatus.accepted).build());
    }

    public Event update(Long id, EventStatus status) {
        Event event = eventRepository.getReferenceById(id);
        event.setStatus(status);
        return eventRepository.save(event);
    }
}
