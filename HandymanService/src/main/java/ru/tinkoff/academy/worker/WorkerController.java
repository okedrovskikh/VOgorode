package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {
    private final WorkerService workerService;

    @PostMapping("")
    public Worker save(@RequestBody WorkerCreateDto workerCreateDto) {
        return this.workerService.save(workerCreateDto);
    }

    @GetMapping("/{id}")
    public ExtendedWorker getById(@PathVariable("id") Long id) {
        return this.workerService.getById(id);
    }

    @PutMapping("")
    public Worker update(@RequestBody WorkerUpdateDto workerUpdateDto) {
        return this.workerService.update(workerUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.workerService.delete(id);
    }
}
