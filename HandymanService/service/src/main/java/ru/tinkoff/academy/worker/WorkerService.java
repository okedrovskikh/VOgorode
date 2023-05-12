package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.exceptions.EntityNotFoundException;
import ru.tinkoff.academy.landscape.UserWebClientHelper;
import ru.tinkoff.academy.landscape.user.LandscapeUserMapper;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;
import ru.tinkoff.academy.landscape.user.LandscapeUser;

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
        worker.setLandscapeUserId(landscapeUser.getId());
        return workerRepository.save(worker);
    }

    public ExtendedByUserWorker getExtendedById(String id) {
        return mapToExtended(getById(id));
    }

    public Worker getById(String id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Worker wasn't find by id: %s", id)));
    }

    public Optional<Worker> findById(String id) {
        return workerRepository.findById(id);
    }

    public List<ExtendedByUserWorker> findAllExtended() {
        return findAll().stream().map(this::mapToExtended).toList();
    }

    private ExtendedByUserWorker mapToExtended(Worker worker) {
        LandscapeUser landscapeUser = webHelper.getUser(worker.getLandscapeUserId()).block();
        return workerMapper.toExtendedWorker(worker, landscapeUser);
    }

    public List<Worker> findAll() {
        return this.workerRepository.findAll();
    }

    @Transactional
    public Worker update(WorkerUpdateDto workerUpdateDto) {
        Worker worker = workerMapper.dtoToWorker(workerUpdateDto);
        Worker oldWorker = getById(worker.getId());
        worker.setLandscapeUserId(oldWorker.getLandscapeUserId());
        return workerRepository.save(worker);
    }

    public void delete(String id) {
        this.workerRepository.deleteById(id);
    }
}
