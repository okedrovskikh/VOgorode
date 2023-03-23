package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerMapper workerMapper;
    private final WorkerRepository workerRepository;

    public Worker save(WorkerCreateDto workerCreateDto) {
        Worker worker = this.workerMapper.dtoToWorker(workerCreateDto);
        return worker;
    }

    public Worker getById(Long id) {
        return null;
    }

    public Worker update(WorkerUpdateDto workerUpdateDto) {
        Worker worker = this.workerMapper.dtoToWorker(workerUpdateDto);
        return worker;
    }

    public void delete(Long id) {

    }
}
