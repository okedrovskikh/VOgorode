package ru.tinkoff.academy.garden;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;
import ru.tinkoff.academy.landscape.WebClientHelper;
import ru.tinkoff.academy.site.Site;
import ru.tinkoff.academy.site.SiteMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GardenService {
    private final SiteMapper siteMapper;
    private final GardenMapper gardenMapper;
    private final GardenRepository gardenRepository;
    private final WebClientHelper webHelper;

    @Transactional
    public Garden save(GardenCreateDto gardenCreateDto) {
        Garden garden = this.gardenMapper.dtoToGarden(gardenCreateDto);
        garden = this.gardenRepository.save(garden);
        webHelper.webClient().post()
                .uri(String.format("/sites/%s", garden.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(this.siteMapper.gardenDtoToSiteCreateDto(gardenCreateDto))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Site.class).block();
        return garden;
    }

    public ExtendedGarden getById(UUID id) {
        Garden garden = this.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Garden wasn't find by id: %s", id)));
        return this.mapToExtended(garden);
    }

    public Optional<Garden> findById(UUID id) {
        return this.gardenRepository.findById(id);
    }

    public List<ExtendedGarden> findAllExtended() {
        return this.findAll().stream().map(this::mapToExtended).toList();
    }

    private ExtendedGarden mapToExtended(Garden garden) {
        Site site = webHelper.webClient().get()
                .uri(String.format("/sites/%s", garden.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Site.class).block();
        return this.gardenMapper.toExtendedGarden(garden, site);
    }

    public List<Garden> findAll() {
        return this.gardenRepository.findAll();
    }

    @Transactional
    public Garden update(GardenUpdateDto gardenUpdateDto) {
        Garden garden = this.gardenMapper.dtoToGarden(gardenUpdateDto);
        if (this.gardenRepository.updateById(UUID.fromString(garden.getId()), garden) == 1) {
            webHelper.webClient().put()
                    .uri("/sites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(this.siteMapper.gardenDtoToSiteUpdateDto(gardenUpdateDto))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Site.class).block();
            return garden;
        }
        throw new IllegalArgumentException("No entity was update");
    }

    public void delete(UUID id) {
        this.gardenRepository.deleteById(id);
    }
}
