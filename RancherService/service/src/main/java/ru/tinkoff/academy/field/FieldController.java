package ru.tinkoff.academy.field;

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
import ru.tinkoff.academy.field.dto.FieldCreateDto;
import ru.tinkoff.academy.field.dto.FieldDto;
import ru.tinkoff.academy.field.dto.FieldUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/fields")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "field"},
        description = "Duration Rancher service Field process handling",
        histogram = true
)
public class FieldController {
    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    @PostMapping("")
    public FieldDto save(@RequestBody FieldCreateDto createDto) {
        return fieldMapper.toDto(fieldService.save(createDto));
    }

    @GetMapping("/{id}")
    public FieldDto getById(@PathVariable("id") Long id) {
        return fieldMapper.toDto(fieldService.getById(id));
    }

    @GetMapping("/all")
    public List<FieldDto> findAll() {
        return fieldService.findAll().stream().map(fieldMapper::toDto).toList();
    }

    @PutMapping("")
    public FieldDto update(@RequestBody FieldUpdateDto updateDto) {
        return fieldMapper.toDto(fieldService.update(updateDto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        fieldService.delete(id);
    }
}
