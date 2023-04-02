package ru.tinkoff.academy.garden;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;
import ru.tinkoff.academy.landscape.WebClientHelper;
import ru.tinkoff.academy.site.Site;
import ru.tinkoff.academy.site.SiteMapper;

import java.util.List;
import java.util.Objects;
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
        Site site = webHelper.webClient().post()
                .uri("/sites")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(this.siteMapper.gardenDtoToSiteCreateDto(gardenCreateDto))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> HttpStatus.CONFLICT == status, response -> {
                    throw new IllegalStateException("Invalid request");
                })
                .onStatus(status -> HttpStatus.INTERNAL_SERVER_ERROR == status, response -> {
                    throw new IllegalStateException("Service unavailable");
                })
                .bodyToMono(Site.class).block();
        Garden garden = this.gardenMapper.dtoToGarden(gardenCreateDto);
        garden.setSiteId(site.getId());
        return this.gardenRepository.save(garden);
    }

    public ExtendedGarden getExtendedById(String id) {
        return this.mapToExtended(this.getById(id));
    }

    public Garden getById(String id) {
        return this.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Garden wasn't find by id: %s", id)));
    }

    public Optional<Garden> findById(String id) {
        return this.gardenRepository.findById(id);
    }

    public List<ExtendedGarden> findAllExtended() {
        return this.findAll().stream().map(this::mapToExtended).toList();
    }

    private ExtendedGarden mapToExtended(Garden garden) {
        Site site = webHelper.webClient().get()
                .uri(String.format("/sites/%s", garden.getSiteId()))
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
        Garden oldGarden = this.getById(garden.getId());
        garden.setSiteId(oldGarden.getSiteId());
        if (this.gardenRepository.updateById(garden.getId(), garden) == 1) {
            webHelper.webClient().put()
                    .uri("/sites")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(this.siteMapper.gardenDtoToSiteUpdateDto(gardenUpdateDto, garden.getSiteId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(status -> HttpStatus.CONFLICT == status, response -> {
                        throw new IllegalStateException("Invalid request");
                    })
                    .onStatus(status -> HttpStatus.INTERNAL_SERVER_ERROR == status, response -> {
                        throw new IllegalStateException("Service unavailable");
                    })
                    .bodyToMono(Site.class).block();
            return garden;
        }
        throw new IllegalArgumentException("No entity was update");
    }

    public void delete(String id) {
        this.gardenRepository.deleteById(id);
    }
}
