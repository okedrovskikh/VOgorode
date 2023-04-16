package ru.tinkoff.academy.gardener;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.gardener.dto.GardenerCreateDto;
import ru.tinkoff.academy.gardener.dto.GardenerUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GardenerService {
    private final GardenerRepository gardenerRepository;
    private final GardenerMapper gardenerMapper;

    public Gardener save(GardenerCreateDto createDto) {
        Gardener gardener = gardenerMapper.dtoToGardener(createDto);
        return gardenerRepository.save(gardener);
    }

    public Gardener getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Gardener wasn't find by id: %s", id)));
    }

    public Optional<Gardener> findById(Long id) {
        return gardenerRepository.findById(id);
    }

    public List<Gardener> findAll() {
        return gardenerRepository.findAll();
    }

    public Gardener update(GardenerUpdateDto updateDto) {
        Gardener gardener = gardenerRepository.getReferenceById(updateDto.getId());
        gardener = gardenerMapper.updateGardener(gardener, updateDto);
        return gardenerRepository.save(gardener);
    }

    public void delete(Long id) {
        gardenerRepository.deleteById(id);
    }
}
