package ru.tinkoff.academy.garden;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;
import ru.tinkoff.academy.site.Site;

@Mapper(componentModel = "spring")
public interface GardenMapper {
    Garden dtoToGarden(GardenCreateDto gardenCreateDto);

    Garden dtoToGarden(GardenUpdateDto gardenUpdateDto);

    @Mapping(target = "id", source = "garden.id")
    @Mapping(target = "latitude", source = "site.latitude")
    @Mapping(target = "longitude", source = "site.longitude")
    ExtendedGarden toExtendedGarden(Garden garden, Site site);
}
