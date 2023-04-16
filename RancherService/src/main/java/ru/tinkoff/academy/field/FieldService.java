package ru.tinkoff.academy.field;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;

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

    public List<Field> findAll() {
        return fieldRepository.findAll();
    }

    public List<Field> findAllByIds(Iterable<Long> ids) {
        return fieldRepository.findAllById(ids);
    }

    public Field update(FieldUpdateDto updateDto) {
        Field field = fieldRepository.getReferenceById(updateDto.getId());
        field = fieldMapper.updateField(field, updateDto);
        return fieldRepository.save(field);
    }

    public void delete(Long id) {
        fieldRepository.deleteById(id);
    }
}
