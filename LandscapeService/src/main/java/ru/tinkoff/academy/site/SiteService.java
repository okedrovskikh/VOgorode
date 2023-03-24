package ru.tinkoff.academy.site;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;
import ru.tinkoff.academy.user.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteService {
    private final SiteMapper siteMapper;
    private final SiteRepository siteRepository;

    public Site save(SiteCreateDto siteCreateDto) {
        Site site = this.siteMapper.dtoToSite(siteCreateDto);
        return this.siteRepository.save(site);
    }

    public Site getById(Long id) {
        return this.findById(id).orElseThrow(() -> new IllegalArgumentException());
    }

    public Optional<Site> findById(Long id) {
        return this.siteRepository.findById(id);
    }

    @Transactional
    public Site update(SiteUpdateDto siteUpdateDto) {
        Site site = this.siteMapper.dtoToSite(siteUpdateDto);
        if (this.siteRepository.update(site) == 1) {
            return site;
        }
        throw new IllegalArgumentException();
    }

    public void delete(Long id) {
        this.siteRepository.deleteById(id);
    }
}
