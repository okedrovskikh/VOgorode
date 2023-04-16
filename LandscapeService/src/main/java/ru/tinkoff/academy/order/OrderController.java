package ru.tinkoff.academy.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.order.dto.OrderCreateDto;
import ru.tinkoff.academy.order.dto.OrderUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public Order save(@RequestBody OrderCreateDto createDto) {
        return orderService.save(createDto);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @GetMapping("")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @PutMapping("")
    public Order update(@RequestBody OrderUpdateDto updateDto) {
        return orderService.update(updateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        orderService.delete(id);
    }
}
