package ru.tinkoff.academy.site;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

@Service
@RequiredArgsConstructor
public class SiteService {
    private final SiteRepository siteRepository;

    public Site save(SiteCreateDto siteCreateDto) {
        return null;
    }

    public Site getById(Long id) {
        return null;
    }

    public Site update(SiteUpdateDto siteUpdateDto) {
        return null;
    }

    public void delete(Long id) {

    }
}
