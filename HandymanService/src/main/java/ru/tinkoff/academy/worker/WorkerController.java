package ru.tinkoff.academy.worker;

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
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

import java.util.List;

@RestController
@RequestMapping("/workers")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"service", "handyman-service", "process", "worker"},
        description = "Duration of Handyman service Worker process handling",
        histogram = true
)
public class WorkerController {
    private final WorkerService workerService;

    @PostMapping("")
    public Worker save(@RequestBody WorkerCreateDto workerCreateDto) {
        return workerService.save(workerCreateDto);
    }

    @GetMapping("/{id}")
    public ExtendedByUserWorker getById(@PathVariable("id") String id) {
        return workerService.getExtendedById(id);
    }

    @GetMapping("/extended/all")
    public List<ExtendedByUserWorker> findAll() {
        return workerService.findAllExtended();
    }

    @PutMapping("")
    public Worker update(@RequestBody WorkerUpdateDto workerUpdateDto) {
        return workerService.update(workerUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        workerService.delete(id);
    }
}
