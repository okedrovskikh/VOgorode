package ru.tinkoff.academy.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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

    public List<Order> findAllByStatus(OrderStatus orderStatus) {
        return orderRepository.findAllByStatus(orderStatus);
    }

    public Page<Order> searchPage(int pageNumber, int pageSize) {
        return orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Order updateWorkerId(Long id, UUID workerId) {
        Order order = orderRepository.getReferenceById(id);
        order.setStatus(OrderStatus.in_progress);
        order.setWorkerId(workerId);
        return orderRepository.save(order);
    }

    public Order update(OrderUpdateDto updateDto) {
        Order order = orderRepository.getReferenceById(updateDto.getId());
        order = orderMapper.updateOrder(order, updateDto);
        return orderRepository.save(order);
    }

    public Order updateStatus(StatusUpdateDto statusUpdateDto) {
        Order order = orderRepository.getReferenceById(statusUpdateDto.getId());
        order.setStatus(statusUpdateDto.getStatus());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
