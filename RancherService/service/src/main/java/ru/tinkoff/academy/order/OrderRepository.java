package ru.tinkoff.academy.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<OrderStatus, String> {
    Optional<OrderStatus> findByGardenId(String gardenId);
}
