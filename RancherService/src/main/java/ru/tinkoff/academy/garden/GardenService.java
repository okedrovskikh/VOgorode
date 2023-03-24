package ru.tinkoff.academy.garden;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;
import ru.tinkoff.academy.site.Site;
import ru.tinkoff.academy.site.SiteMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GardenService {
    private final SiteMapper siteMapper;
    private final GardenMapper gardenMapper;
    private final GardenRepository gardenRepository;
    private final String uri = "http://localhost:8082";

    public Garden save(GardenCreateDto gardenCreateDto) {
        WebClient client = WebClient.create(this.uri);
        Mono<Site> siteMono = client.post().uri("/sites")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(this.siteMapper.gardenCreateDtoToSiteCreateDto(gardenCreateDto))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Site.class);
        Site site = siteMono.block();
        Garden garden = this.gardenMapper.dtoToGarden(gardenCreateDto);
        garden.setLandscapeId(site.getId());
        return this.gardenRepository.save(garden);
    }

    public ExtendedGarden getById(Long id) {
        Garden garden = this.findById(id).orElseThrow(() -> new IllegalArgumentException());
        WebClient client = WebClient.create(this.uri);
        Mono<Site> site = client.get().uri(String.format("/sites/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Site.class);
        return this.gardenMapper.toExtendedGarden(garden, site.block());
    }

    public Optional<Garden> findById(Long id) {
        return this.gardenRepository.findById(id);
    }

    public Garden update(GardenUpdateDto gardenUpdateDto) {
        Garden garden = this.gardenMapper.dtoToGarden(gardenUpdateDto);
        if (this.gardenRepository.update(garden.getId(), garden) == 1) {
            return garden;
        }
        throw new IllegalArgumentException();
    }

    public void delete(Long id) {
        this.gardenRepository.deleteById(id);
    }
}
