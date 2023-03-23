package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerRepository workerRepository;

    public Worker save(WorkerCreateDto workerCreateDto) {
        return null;
    }

    public Worker getById(Long id) {
        return null;
    }

    public Worker update(WorkerUpdateDto workerUpdateDto) {
        return null;
    }

    public void delete(Long id) {

    }
}
