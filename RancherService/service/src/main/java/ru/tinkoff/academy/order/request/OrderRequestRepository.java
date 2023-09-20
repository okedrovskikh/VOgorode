package ru.tinkoff.academy.order.request;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRequestRepository extends MongoRepository<OrderStatus, String> {
    Optional<OrderStatus> findByGardenId(String gardenId);

    Optional<OrderStatus> findByOrderId(Long orderId);
}
