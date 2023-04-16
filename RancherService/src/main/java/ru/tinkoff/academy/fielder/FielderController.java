package ru.tinkoff.academy.fielder;

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
import ru.tinkoff.academy.fielder.dto.FielderUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/gardeners")
@RequiredArgsConstructor
public class FielderController {
    private final FielderService fielderService;

    @PostMapping("")
    public Fielder save(@RequestBody FielderCreateDto createDto) {
        return fielderService.save(createDto);
    }

    @GetMapping("/{id}")
    public Fielder getById(@PathVariable("id") Long id) {
        return fielderService.getById(id);
    }

    @GetMapping("")
    public List<Fielder> findAll() {
        return fielderService.findAll();
    }

    @PutMapping("")
    public Fielder update(FielderUpdateDto updateDto) {
        return fielderService.update(updateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        fielderService.delete(id);
    }
}
