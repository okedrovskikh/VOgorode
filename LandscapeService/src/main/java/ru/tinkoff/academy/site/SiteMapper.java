package ru.tinkoff.academy.site;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

@Mapper(componentModel = "spring")
public abstract class SiteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "latitude", expression = "java(this.fromStringToDouble(siteCreateDto.getLatitude()))")
    @Mapping(target = "longitude", expression = "java(this.fromStringToDouble(siteCreateDto.getLongitude()))")
    public abstract Site dtoToSite(SiteCreateDto siteCreateDto);

    @Mapping(target = "latitude", expression = "java(this.fromStringToDouble(siteUpdateDto.getLatitude()))")
    @Mapping(target = "longitude", expression = "java(this.fromStringToDouble(siteUpdateDto.getLongitude()))")
    public abstract Site dtoToSite(SiteUpdateDto siteUpdateDto);

    protected final Double fromStringToDouble(String s) {
        if (s == null) {
            return null;
        }

        return Double.valueOf(s);
    }
}
