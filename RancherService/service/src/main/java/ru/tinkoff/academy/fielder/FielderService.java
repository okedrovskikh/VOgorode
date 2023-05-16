package ru.tinkoff.academy.fielder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.exceptions.EntityNotFoundException;
import ru.tinkoff.academy.field.Field;
import ru.tinkoff.academy.field.FieldService;
import ru.tinkoff.academy.fielder.dto.FielderCreateDto;
import ru.tinkoff.academy.fielder.dto.FielderUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FielderService {
    private final FieldService fieldService;
    private final FielderRepository fielderRepository;
    private final FielderMapper fielderMapper;

    @Transactional
    public Fielder save(FielderCreateDto createDto) {
        Fielder fielder = fielderMapper.dtoToFielder(createDto);
        List<Field> fields = fieldService.updateFieldsFielder(createDto.getFieldsId(), fielder);
        fielder.setFields(fields);
        return fielderRepository.save(fielder);
    }

    public Fielder getById(String id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Fielder wasn't find by id: %s", id)));
    }

    public Optional<Fielder> findById(String id) {
        return fielderRepository.findById(id);
    }

    public List<Fielder> findAll() {
        return fielderRepository.findAll();
    }

    @Transactional
    public Fielder update(FielderUpdateDto updateDto) {
        Fielder fielder = getById(updateDto.getId());
        fielder = fielderMapper.updateFielder(fielder, updateDto);
        return fielderRepository.save(fielder);
    }

    public void delete(String id) {
        fielderRepository.deleteById(id);
    }
}
