package ru.tinkoff.academy.garden;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;

@Service
@RequiredArgsConstructor
public class GardenService {
    private final GardenMapper gardenMapper;
    private final GardenRepository gardenRepository;

    public Garden save(GardenCreateDto gardenCreateDto) {
        Garden garden = this.gardenMapper.dtoToGarden(gardenCreateDto);
        return garden;
    }

    public Garden getById(Long id) {
        return null;
    }

    public Garden update(GardenUpdateDto gardenUpdateDto) {
        Garden garden = this.gardenMapper.dtoToGarden(gardenUpdateDto);
        return garden;
    }

    public void delete(Long id) {

    }
}
