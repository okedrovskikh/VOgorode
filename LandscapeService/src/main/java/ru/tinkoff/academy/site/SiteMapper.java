package ru.tinkoff.academy.site;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

@Mapper(componentModel = "spring")
public interface SiteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", expression = "java(java.lang.Double.valueOf(siteCreateDto.getLatitude()))")
    @Mapping(target = "longitude", expression = "java(java.lang.Double.valueOf(siteCreateDto.getLongitude()))")
    Site dtoToSite(SiteCreateDto siteCreateDto);

    @Mapping(target = "latitude", expression = "java(java.lang.Double.valueOf(siteUpdateDto.getLatitude()))")
    @Mapping(target = "longitude", expression = "java(java.lang.Double.valueOf(siteUpdateDto.getLongitude()))")
    Site dtoToSite(SiteUpdateDto siteUpdateDto);
}
