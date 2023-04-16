package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.configuration.landscape.UserWebClientHelper;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;
import ru.tinkoff.academy.landscape.user.LandscapeUser;
import ru.tinkoff.academy.landscape.user.LandscapeUserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerMapper workerMapper;
    private final LandscapeUserMapper landscapeUserMapper;
    private final WorkerRepository workerRepository;
    private final UserWebClientHelper webHelper;

    @Transactional
    public Worker save(WorkerCreateDto workerCreateDto) {
        LandscapeUser landscapeUser = webHelper.saveUser(
                landscapeUserMapper.workerCreateDtoToUserCreateDto(workerCreateDto)
        ).block();
        Worker worker = workerMapper.dtoToWorker(workerCreateDto);
        worker.setUserId(landscapeUser.getId());
        return workerRepository.save(worker);
    }

    public ExtendedByUserWorker getExtendedById(String id) {
        return mapToExtended(getById(id));
    }

    public Worker getById(String id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Worker wasn't find by id: %s", id)));
    }

    public Optional<Worker> findById(String id) {
        return workerRepository.findById(id);
    }

    public List<ExtendedByUserWorker> findAllExtended() {
        return findAll().stream().map(this::mapToExtended).toList();
    }

    private ExtendedByUserWorker mapToExtended(Worker worker) {
        LandscapeUser landscapeUser = this.webHelper.getUser(worker.getUserId()).block();
        return this.workerMapper.toExtendedWorker(worker, landscapeUser);
    }

    public List<Worker> findAll() {
        return this.workerRepository.findAll();
    }

    @Transactional
    public Worker update(WorkerUpdateDto workerUpdateDto) {
        Worker worker = this.workerMapper.dtoToWorker(workerUpdateDto);
        Worker oldWorker = this.getById(worker.getId());
        worker.setUserId(oldWorker.getUserId());
        return workerRepository.save(worker);
    }

    public void delete(String id) {
        this.workerRepository.deleteById(id);
    }
}
