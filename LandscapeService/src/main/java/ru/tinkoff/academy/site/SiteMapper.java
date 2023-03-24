package ru.tinkoff.academy.site;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

@Mapper(componentModel = "spring")
public interface SiteMapper {
    @Mapping(target = "id", ignore = true)
    Site dtoToSite(SiteCreateDto siteCreateDto);

    Site dtoToSite(SiteUpdateDto siteUpdateDto);
}
