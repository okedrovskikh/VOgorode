package ru.tinkoff.academy.gardener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.exceptions.EntityNotFoundException;
import ru.tinkoff.academy.field.Field;
import ru.tinkoff.academy.field.FieldService;
import ru.tinkoff.academy.gardener.dto.GardenerCreateDto;
import ru.tinkoff.academy.gardener.dto.GardenerUpdateDto;
import ru.tinkoff.academy.landscape.order.Order;
import ru.tinkoff.academy.landscape.order.dto.OrderCreateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GardenerService {
    private final FieldService fieldService;
    private final GardenerRepository gardenerRepository;
    private final GardenerMapper gardenerMapper;

    @Transactional
    public Gardener save(GardenerCreateDto createDto) {
        Gardener gardener = gardenerMapper.dtoToFielder(createDto);
        List<Field> fields = fieldService.updateFieldsFielder(createDto.getFieldsId(), gardener);
        gardener.setFields(fields);
        return gardenerRepository.save(gardener);
    }

    public Order createOrder(OrderCreateDto orderCreateDto) {
        return null;
    }

    public Gardener getById(String id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Fielder wasn't find by id: %s", id)));
    }

    public Optional<Gardener> findById(String id) {
        return gardenerRepository.findById(id);
    }

    public List<Gardener> findAll() {
        return gardenerRepository.findAll();
    }

    @Transactional
    public Gardener update(GardenerUpdateDto updateDto) {
        Gardener gardener = getById(updateDto.getId());
        gardener = gardenerMapper.updateFielder(gardener, updateDto);
        return gardenerRepository.save(gardener);
    }

    public void delete(String id) {
        gardenerRepository.deleteById(id);
    }
}
