package ru.tinkoff.academy.site;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.site.dto.SiteCreateDto;
import ru.tinkoff.academy.site.dto.SiteUpdateDto;

@RestController()
@RequestMapping("/sites")
@RequiredArgsConstructor
public class SiteController {
    private final SiteService siteService;

    @PostMapping("")
    public Site save(@RequestBody SiteCreateDto siteCreateDto) {
        return this.siteService.save(siteCreateDto);
    }

    @GetMapping("/{id}")
    public Site getById(@PathVariable("id") Long id) {
        return this.siteService.getById(id);
    }

    @PutMapping
    public Site update(@RequestBody SiteUpdateDto siteUpdateDto) {
        return this.siteService.update(siteUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.siteService.delete(id);
    }
}
