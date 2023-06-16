package ru.tinkoff.academy.gardener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.gardener.dto.GardenerCreateDto;
import ru.tinkoff.academy.gardener.dto.GardenerUpdateDto;
import ru.tinkoff.academy.gardener.order.OrderCreationService;
import ru.tinkoff.academy.landscape.order.Order;
import ru.tinkoff.academy.landscape.order.dto.OrderCreateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GardenerServiceFacade {
    private final GardenerService gardenerService;
    private final OrderCreationService orderCreationService;


    public Gardener save(GardenerCreateDto createDto) {
        return gardenerService.save(createDto);
    }

    public Gardener getById(String id) {
        return gardenerService.getById(id);
    }

    public Optional<Gardener> findById(String id) {
        return gardenerService.findById(id);
    }

    public List<Gardener> findAll() {
        return gardenerService.findAll();
    }

    public Gardener update(GardenerUpdateDto updateDto) {
        return gardenerService.update(updateDto);
    }

    public void delete(String id) {
        gardenerService.delete(id);
    }

    public Order createOrder(OrderCreateDto createDto) {
        return orderCreationService.create(createDto);
    }
}
