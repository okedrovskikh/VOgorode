package ru.tinkoff.academy.fielder;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.fielder.dto.FielderCreateDto;
import ru.tinkoff.academy.fielder.dto.FielderUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FielderService {
    private final FielderRepository fielderRepository;
    private final FielderMapper fielderMapper;

    public Fielder save(FielderCreateDto createDto) {
        Fielder fielder = fielderMapper.dtoToGardener(createDto);
        return fielderRepository.save(fielder);
    }

    public Fielder getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Gardener wasn't find by id: %s", id)));
    }

    public Optional<Fielder> findById(Long id) {
        return fielderRepository.findById(id);
    }

    public List<Fielder> findAll() {
        return fielderRepository.findAll();
    }

    public Fielder update(FielderUpdateDto updateDto) {
        Fielder fielder = fielderRepository.getReferenceById(updateDto.getId());
        fielder = fielderMapper.updateGardener(fielder, updateDto);
        return fielderRepository.save(fielder);
    }

    public void delete(Long id) {
        fielderRepository.deleteById(id);
    }
}
