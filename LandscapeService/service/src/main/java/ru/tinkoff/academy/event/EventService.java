package ru.tinkoff.academy.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.order.Order;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event save(Order order) {
        return eventRepository.save(Event.builder().identityKey(order.getId()).status(EventStatus.accepted).build());
    }

    public Event update(Long id, EventStatus status) {
        Event event = eventRepository.findByIdentityKey(id).get();
        event.setStatus(status);
        return eventRepository.save(event);
    }

    public List<Event> findAllByStatus(EventStatus status) {
        return eventRepository.findAllByStatus(status);
    }
}
