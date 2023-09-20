package ru.tinkoff.academy.gardener;

import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
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
import ru.tinkoff.academy.gardener.dto.GardenerDto;
import ru.tinkoff.academy.gardener.dto.GardenerUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/fielders")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "fielder"},
        description = "Duration Rancher service Fielder process handling",
        histogram = true
)
public class GardenerController {
    private final GardenerService gardenerService;
    private final GardenerMapper gardenerMapper;

    @PostMapping("")
    public GardenerDto save(@Valid @RequestBody GardenerCreateDto createDto) {
        return gardenerMapper.toDto(gardenerService.save(createDto));
    }

    @GetMapping("/{id}")
    public GardenerDto getById(@PathVariable("id") String id) {
        return gardenerMapper.toDto(gardenerService.getById(id));
    }

    @GetMapping("/all")
    public List<GardenerDto> findAll() {
        return gardenerService.findAll().stream().map(gardenerMapper::toDto).toList();
    }

    @PutMapping("")
    public GardenerDto update(@Valid @RequestBody GardenerUpdateDto updateDto) {
        return gardenerMapper.toDto(gardenerService.update(updateDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        gardenerService.delete(id);
    }
}
