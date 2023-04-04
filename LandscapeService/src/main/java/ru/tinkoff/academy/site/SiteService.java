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
        Site site = siteMapper.dtoToSite(siteCreateDto);
        return siteRepository.save(site);
    }

    public Site getById(UUID id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Site wasn't find by id: %s", id)));
    }

    public Optional<Site> findById(UUID id) {
        return siteRepository.findById(id);
    }

    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    public List<Site> findAllById(Iterable<UUID> ids) {
        return siteRepository.findAllById(ids);
    }

    @Transactional
    public Site update(SiteUpdateDto siteUpdateDto) {
        Site site = siteRepository.getReferenceById(siteUpdateDto.getId());
        site = siteMapper.updateSite(siteUpdateDto, site);
        return siteRepository.save(site);
    }

    public Site updateLatitudeAndLongitude(UUID id, String latitude, String longitude) {
        Site site = siteRepository.getReferenceById(id);
        site.setLatitude(Double.valueOf(latitude));
        site.setLongitude(Double.valueOf(longitude));
        return siteRepository.save(site);
    }

    public void delete(UUID id) {
        siteRepository.deleteById(id);
    }
}
