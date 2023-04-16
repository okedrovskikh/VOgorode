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

    @PostMapping("")
    public Field save(@RequestBody FieldCreateDto createDto) {
        return fieldService.save(createDto);
    }

    @GetMapping("/{id}")
    public Field getById(@PathVariable("id") Long id) {
        return fieldService.getById(id);
    }

    @GetMapping("")
    public List<Field> findAll() {
        return fieldService.findAll();
    }

    @PutMapping("")
    public Field update(@RequestBody FieldUpdateDto updateDto) {
        return fieldService.update(updateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        fieldService.delete(id);
    }
}
