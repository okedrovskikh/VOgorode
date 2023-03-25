package ru.tinkoff.academy.garden;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;
import ru.tinkoff.academy.site.Site;

@Mapper(componentModel = "spring")
public interface GardenMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "square", expression = "java(ru.tinkoff.academy.math.RancherMath.square(gardenCreateDto.getX1(), gardenCreateDto.getY1(), gardenCreateDto.getX2(), gardenCreateDto.getY2()))")
    Garden dtoToGarden(GardenCreateDto gardenCreateDto);

    @Mapping(target = "square", expression = "java(ru.tinkoff.academy.math.RancherMath.square(gardenUpdateDto.getX1(), gardenUpdateDto.getY1(), gardenUpdateDto.getX2(), gardenUpdateDto.getY2()))")
    Garden dtoToGarden(GardenUpdateDto gardenUpdateDto);

    @Mapping(target = "id", source = "garden.id")
    @Mapping(target = "latitude", source = "site.latitude")
    @Mapping(target = "longitude", source = "site.longitude")
    ExtendedGarden toExtendedGarden(Garden garden, Site site);
}
