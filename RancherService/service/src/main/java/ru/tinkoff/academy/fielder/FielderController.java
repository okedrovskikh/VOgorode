package ru.tinkoff.academy.fielder;

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
import ru.tinkoff.academy.fielder.dto.FielderCreateDto;
import ru.tinkoff.academy.fielder.dto.FielderDto;
import ru.tinkoff.academy.fielder.dto.FielderUpdateDto;

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
public class FielderController {
    private final FielderService fielderService;
    private final FielderMapper fielderMapper;

    @PostMapping("")
    public FielderDto save(@RequestBody FielderCreateDto createDto) {
        return fielderMapper.toDto(fielderService.save(createDto));
    }

    @GetMapping("/{id}")
    public FielderDto getById(@PathVariable("id") String id) {
        return fielderMapper.toDto(fielderService.getById(id));
    }

    @GetMapping("/all")
    public List<FielderDto> findAll() {
        return fielderService.findAll().stream().map(fielderMapper::toDto).toList();
    }

    @PutMapping("")
    public FielderDto update(@RequestBody FielderUpdateDto updateDto) {
        return fielderMapper.toDto(fielderService.update(updateDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        fielderService.delete(id);
    }
}
