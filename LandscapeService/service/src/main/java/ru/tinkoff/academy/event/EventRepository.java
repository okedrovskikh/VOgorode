package ru.tinkoff.academy.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByIdentityKey(Long identityKey);

    List<Event> findAllByStatus(EventStatus status);
}
