package ru.tinkoff.academy.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.exceptions.IllegalOrderStatusException;
import ru.tinkoff.academy.order.dto.OrderCreateDto;
import ru.tinkoff.academy.order.dto.OrderUpdateDto;
import ru.tinkoff.academy.order.dto.StatusUpdateDto;
import ru.tinkoff.academy.order.status.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Order save(OrderCreateDto createDto) {
        Order order = orderMapper.dtoToOrder(createDto);
        return orderRepository.save(order);
    }

    public Order getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order wasn't find by id: %s", id)));
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Page<Order> searchPage(int pageNumber, int pageSize) {
        return orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Order updateWorkerId(Long id, UUID workerId) {
        Order order = orderRepository.getReferenceById(id);

        if (!order.getStatus().equals(OrderStatus.created)) {
            throw new IllegalOrderStatusException(String.format("Order status already not created, status: %s", order.getStatus()));
        }

        order.setStatus(OrderStatus.in_progress);
        order.setWorkerId(workerId);
        return orderRepository.save(order);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Order update(OrderUpdateDto updateDto) {
        Order order = orderRepository.getReferenceById(updateDto.getId());
        order = orderMapper.updateOrder(order, updateDto);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.getReferenceById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
