package ru.tinkoff.academy.field;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;
import ru.tinkoff.academy.fielder.Fielder;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {
    private final FieldRepository fieldRepository;
    private final FieldMapper fieldMapper;

    public Field save(FieldCreateDto createDto) {
        Field field = fieldMapper.dtoToField(createDto);
        return fieldRepository.save(field);
    }

    public Field getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Field wasn't find by id: %s", id)));
    }

    public Optional<Field> findById(Long id) {
        return fieldRepository.findById(id);
    }

    public List<Object[]> findAreasStatBySplitValue(Double splitValue) {
        return fieldRepository.findAreasStatBySplitValue(splitValue).stream()
                .map(stats -> (Object[]) stats)
                .toList();
    }

    public List<Object[]> findAreasStat() {
        return fieldRepository.findAreasStat().stream()
                .map(stats -> (Object[]) stats)
                .toList();
    }

    public List<Field> findAll() {
        return fieldRepository.findAll();
    }

    public List<Field> findAllByIds(Iterable<Long> ids) {
        return fieldRepository.findAllById(ids);
    }

    @Transactional
    public Field update(FieldUpdateDto updateDto) {
        Field field = fieldRepository.getReferenceById(updateDto.getId());
        field = fieldMapper.updateField(field, updateDto);
        return fieldRepository.save(field);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Field> updateFieldsFielder(List<Long> fieldsId, Fielder fielder) {
        List<Field> fields = findAllByIds(fieldsId).stream().peek((field) -> field.setFielder(fielder)).toList();
        return fieldRepository.saveAll(fields);
    }

    public void delete(Long id) {
        fieldRepository.deleteById(id);
    }
}