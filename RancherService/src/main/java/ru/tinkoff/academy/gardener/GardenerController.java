package ru.tinkoff.academy.gardener;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.gardener.dto.GardenerCreateDto;
import ru.tinkoff.academy.gardener.dto.GardenerUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/gardeners")
@RequiredArgsConstructor
public class GardenerController {
    private final GardenerService gardenerService;

    @PostMapping("")
    public Gardener save(@RequestBody GardenerCreateDto createDto) {
        return gardenerService.save(createDto);
    }

    @GetMapping("/{id}")
    public Gardener getById(@PathVariable("id") Long id) {
        return gardenerService.getById(id);
    }

    @GetMapping("")
    public List<Gardener> findAll() {
        return gardenerService.findAll();
    }

    @PutMapping("")
    public Gardener update(GardenerUpdateDto updateDto) {
        return gardenerService.update(updateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        gardenerService.delete(id);
    }
}