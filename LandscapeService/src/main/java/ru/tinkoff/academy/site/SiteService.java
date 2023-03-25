package ru.tinkoff.academy.site;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

import java.util.List;
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

    public Site save(String id, SiteCreateDto siteCreateDto) {
        Site site = this.siteMapper.dtoToSite(siteCreateDto);
        site.setId(id);
        return this.siteRepository.save(site);
    }

    public Site getById(String id) {
        return this.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Site wasn't find by id: %s", id)));
    }

    public Optional<Site> findById(String id) {
        return this.siteRepository.findById(id);
    }

    public List<Site> findAll() {
        return this.siteRepository.findAll();
    }

    public List<Site> findAllById(Iterable<String> ids) {
        return this.siteRepository.findAllById(ids);
    }

    @Transactional
    public Site update(SiteUpdateDto siteUpdateDto) {
        Site site = this.siteMapper.dtoToSite(siteUpdateDto);
        if (this.siteRepository.update(site) == 1) {
            return site;
        }
        throw new IllegalArgumentException("No site was update");
    }

    public void delete(String id) {
        this.siteRepository.deleteById(id);
    }
}
