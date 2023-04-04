package ru.tinkoff.academy.garden;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;
import ru.tinkoff.academy.landscape.SiteWebClientHelper;
import ru.tinkoff.academy.site.Site;
import ru.tinkoff.academy.site.SiteMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GardenService {
    private final SiteMapper siteMapper;
    private final GardenMapper gardenMapper;
    private final GardenRepository gardenRepository;
    private final SiteWebClientHelper webHelper;

    @Transactional
    public Garden save(GardenCreateDto gardenCreateDto) {
        Site site = webHelper.saveSite(siteMapper.gardenDtoToSiteCreateDto(gardenCreateDto)).block();
        Garden garden = gardenMapper.dtoToGarden(gardenCreateDto);
        garden.setSiteId(site.getId());
        return gardenRepository.save(garden);
    }

    public ExtendedGarden getExtendedById(String id) {
        return mapToExtended(getById(id));
    }

    public Garden getById(String id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Garden wasn't find by id: %s", id)));
    }

    public Optional<Garden> findById(String id) {
        return gardenRepository.findById(id);
    }

    public List<ExtendedGarden> findAllExtended() {
        return findAll().stream().map(this::mapToExtended).toList();
    }

    private ExtendedGarden mapToExtended(Garden garden) {
        Site site = webHelper.getSiteById(garden.getSiteId()).block();
        return gardenMapper.toExtendedGarden(garden, site);
    }

    public List<Garden> findAll() {
        return gardenRepository.findAll();
    }

    @Transactional
    public Garden update(GardenUpdateDto gardenUpdateDto) {
        Garden garden = gardenMapper.dtoToGarden(gardenUpdateDto);
        Garden oldGarden = getById(garden.getId());
        garden.setSiteId(oldGarden.getSiteId());
        return gardenRepository.save(garden);
    }

    public void delete(String id) {
        gardenRepository.deleteById(id);
    }
}
