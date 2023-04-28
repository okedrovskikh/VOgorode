package ru.tinkoff.academy.site;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/sites")
@RequiredArgsConstructor
public class SiteController {
    private final SiteService siteService;

    @PostMapping("")
    public Site save(@RequestBody SiteCreateDto siteCreateDto) {
        return siteService.save(siteCreateDto);
    }

    @GetMapping("/{id}")
    public Site getById(@PathVariable("id") UUID id) {
        return siteService.getById(id);
    }

    @GetMapping("/all")
    public List<Site> findAll() {
        return siteService.findAll();
    }

    @GetMapping("")
    public List<Site> findAllById(@RequestParam("ids") List<UUID> ids) {
        return siteService.findAllById(ids);
    }

    @PutMapping
    public Site update(@RequestBody SiteUpdateDto siteUpdateDto) {
        return siteService.update(siteUpdateDto);
    }

    @PutMapping("/{id}")
    public Site updateLatitudeAndLongitude(@PathVariable("id") UUID id, @RequestParam(name = "lat") String latitude,
                                           @RequestParam(name = "lon") String longitude) {
        return siteService.updateLatitudeAndLongitude(id, latitude, longitude);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        siteService.delete(id);
    }
}
