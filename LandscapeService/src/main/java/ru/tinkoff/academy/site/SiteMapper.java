package ru.tinkoff.academy.site;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

@Mapper(componentModel = "spring")
public abstract class SiteMapper {
    @Mapping(target = "id", ignore = true)
    public abstract Site dtoToSite(SiteCreateDto siteCreateDto);

    public Site updateSite(SiteUpdateDto siteUpdateDto, Site site) {
        if (siteUpdateDto.getLatitude() != null) {
            site.setLatitude(Double.valueOf(siteUpdateDto.getLatitude()));
        }

        if (siteUpdateDto.getLongitude() != null) {
            site.setLongitude(Double.valueOf(siteUpdateDto.getLongitude()));
        }

        return site;
    }
}
