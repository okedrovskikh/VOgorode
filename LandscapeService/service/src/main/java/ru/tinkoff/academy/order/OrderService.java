package ru.tinkoff.academy.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.order.dto.OrderCreateDto;
import ru.tinkoff.academy.order.dto.OrderUpdateDto;

import java.util.List;
import java.util.Optional;

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

    public Page<Order> searchPage(int pageNumber, int pageSize) {
        return orderRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Order update(OrderUpdateDto updateDto) {
        Order order = orderRepository.getReferenceById(updateDto.getId());
        order = orderMapper.updateOrder(order, updateDto);
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
