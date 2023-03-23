package ru.tinkoff.academy.garden;

import org.mapstruct.Mapper;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;

@Mapper(componentModel = "spring")
public interface GardenMapper {
    Garden dtoToGarden(GardenCreateDto gardenCreateDto);

    Garden dtoToGarden(GardenUpdateDto gardenUpdateDto);
}
