package ru.tinkoff.academy.site;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.site.dto.SiteCreateDto;

@Mapper(componentModel = "spring")
public interface SiteMapper {
    @Mapping(target = "latitude", source = "gardenCreateDto.x1")
    @Mapping(target = "longitude", source = "gardenCreateDto.y1")
    SiteCreateDto gardenCreateDtoToSiteCreateDto(GardenCreateDto gardenCreateDto);
}
