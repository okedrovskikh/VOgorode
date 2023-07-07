package ru.tinkoff.academy.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<UUID, Rating> {
    @Query(nativeQuery = true, value = "select avg(rating) from rating where worker_id = :workerId")
    Object getLastOrdersRating(Long ordersNumber, UUID workerId);
}

