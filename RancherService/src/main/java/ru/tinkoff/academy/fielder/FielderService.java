package ru.tinkoff.academy.fielder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Fielder save(FielderCreateDto createDto) {
        Fielder fielder = fielderMapper.dtoToFielder(createDto);
        return fielderRepository.save(fielder);
    }

    public Fielder getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Fielder wasn't find by id: %s", id)));
    }

    public Optional<Fielder> findById(Long id) {
        return fielderRepository.findById(id);
    }

    public Double[] getAreasStatByEmail(String email) {
        return findAreasStatByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Fielder by email=%s, telephone=%s doesn't have fields", email, null)));
    }

    public Optional<Double[]> findAreasStatByEmail(String email) {
        return fielderRepository.findAreasStatByEmail(email);
    }

    public Double[] getAreasStatByEmailAndTelephone(String email, String telephone) {
        return findAreasStatByEmailAndTelephone(email, telephone)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Fielder by email=%s, telephone=%s doesn't have fields", email, telephone)));
    }

    public Optional<Double[]> findAreasStatByEmailAndTelephone(String email, String telephone) {
        return fielderRepository.findAreasStatByEmailAndTelephone(email, telephone);
    }

    public List<Fielder> findAll() {
        return fielderRepository.findAll();
    }

    @Transactional
    public Fielder update(FielderUpdateDto updateDto) {
        Fielder fielder = fielderRepository.getReferenceById(updateDto.getId());
        fielder = fielderMapper.updateFielder(fielder, updateDto);
        return fielderRepository.save(fielder);
    }

    public void delete(Long id) {
        fielderRepository.deleteById(id);
    }
}
