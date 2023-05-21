package ru.tinkoff.academy.order;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.order.dto.OrderCreateDto;
import ru.tinkoff.academy.order.dto.OrderUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "order"},
        description = "Duration Landscape service Order process handling",
        histogram = true
)
public class OrderController {
    private final OrderService orderService;

    @PostMapping("")
    public Order save(@RequestBody @Valid OrderCreateDto createDto) {
        return orderService.save(createDto);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @GetMapping("/all")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("")
    public Page<Order> searchPage(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                  @RequestParam(value = "size", defaultValue = "10") int pageSize) {
        return orderService.searchPage(pageNumber, pageSize);
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
