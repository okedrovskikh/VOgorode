package ru.tinkoff.academy.site;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SiteService {
    private final SiteMapper siteMapper;
    private final SiteRepository siteRepository;

    public Site save(SiteCreateDto siteCreateDto) {
        Site site = this.siteMapper.dtoToSite(siteCreateDto);
        return this.siteRepository.save(site);
    }

    public Site getById(UUID id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Site wasn't find by id: %s", id)));
    }

    public Optional<Site> findById(UUID id) {
        return this.siteRepository.findById(id);
    }

    public List<Site> findAll() {
        return this.siteRepository.findAll();
    }

    public List<Site> findAllById(Iterable<UUID> ids) {
        return this.siteRepository.findAllById(ids);
    }

    @Transactional
    public Site update(SiteUpdateDto siteUpdateDto) {
        Site site = this.siteRepository.getReferenceById(siteUpdateDto.getId());
        site = this.siteMapper.updateSite(siteUpdateDto, site);
        return this.siteRepository.save(site);
    }

    public void delete(UUID id) {
        this.siteRepository.deleteById(id);
    }
}
