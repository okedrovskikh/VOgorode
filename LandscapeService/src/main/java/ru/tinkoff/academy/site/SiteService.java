package ru.tinkoff.academy.site;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

@Service
@RequiredArgsConstructor
public class SiteService {
    private final SiteMapper siteMapper;
    private final SiteRepository siteRepository;

    public Site save(SiteCreateDto siteCreateDto) {
        Site site = this.siteMapper.dtoToSite(siteCreateDto);
        return site;
    }

    public Site getById(Long id) {
        return null;
    }

    public Site update(SiteUpdateDto siteUpdateDto) {
        Site site = this.siteMapper.dtoToSite(siteUpdateDto);
        return site;
    }

    public void delete(Long id) {

    }
}
