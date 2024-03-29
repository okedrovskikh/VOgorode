package ru.tinkoff.academy.landscape.site;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;
import ru.tinkoff.academy.landscape.site.dto.SiteCreateDto;
import ru.tinkoff.academy.landscape.site.dto.SiteUpdateDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SiteMapper {
    @Mapping(target = "latitude", expression = "java((gardenCreateDto.getX1() + gardenCreateDto.getX2()) / 2)")
    @Mapping(target = "longitude", expression = "java((gardenCreateDto.getY1() + gardenCreateDto.getY2()) / 2)")
    SiteCreateDto gardenDtoToSiteCreateDto(GardenCreateDto gardenCreateDto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "latitude", expression = "java((gardenUpdateDto.getX1() + gardenUpdateDto.getX2()) / 2)")
    @Mapping(target = "longitude", expression = "java((gardenUpdateDto.getY1() + gardenUpdateDto.getY2()) / 2)")
    SiteUpdateDto gardenDtoToSiteUpdateDto(GardenUpdateDto gardenUpdateDto, UUID id);
}
