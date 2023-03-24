package ru.tinkoff.academy.garden;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.garden.dto.GardenCreateDto;
import ru.tinkoff.academy.garden.dto.GardenUpdateDto;

@RestController()
@RequestMapping("/gardens")
@RequiredArgsConstructor
public class GardenController {
    private final GardenService gardenService;

    @PostMapping("")
    public Garden save(@RequestBody GardenCreateDto gardenCreateDto) {
        return this.gardenService.save(gardenCreateDto);
    }

    @GetMapping("/{id}")
    public ExtendedGarden getById(@PathVariable("id") Long id) {
        return this.gardenService.getById(id);
    }

    @PutMapping
    public Garden update(@RequestBody GardenUpdateDto gardenUpdateDto) {
        return this.gardenService.update(gardenUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.gardenService.delete(id);
    }
}
