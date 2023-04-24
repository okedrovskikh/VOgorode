package ru.tinkoff.academy.garden;

import io.micrometer.core.annotation.Timed;
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

import java.util.List;

@RestController()
@RequestMapping("/gardens")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "garden"},
        description = "Duration of Rancher service Garden process handling",
        histogram = true
)
public class GardenController {
    private final GardenService gardenService;

    @PostMapping("")
    public Garden save(@RequestBody GardenCreateDto gardenCreateDto) {
        return this.gardenService.save(gardenCreateDto);
    }

    @GetMapping("/{id}")
    public ExtendedGarden getById(@PathVariable("id") String id) {
        return this.gardenService.getExtendedById(id);
    }

    @GetMapping("/extended/all")
    public List<ExtendedGarden> findAllExtended() {
        return this.gardenService.findAllExtended();
    }

    @PutMapping
    public Garden update(@RequestBody GardenUpdateDto gardenUpdateDto) {
        return this.gardenService.update(gardenUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        this.gardenService.delete(id);
    }
}
