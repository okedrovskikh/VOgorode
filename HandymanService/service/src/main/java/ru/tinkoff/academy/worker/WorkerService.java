package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.exceptions.EntityNotFoundException;
import ru.tinkoff.academy.landscape.account.AccountWebClientHelper;
import ru.tinkoff.academy.landscape.account.Account;
import ru.tinkoff.academy.landscape.account.AccountMapper;
import ru.tinkoff.academy.work.WorkEnum;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerMapper workerMapper;
    private final AccountMapper accountMapper;
    private final WorkerRepository workerRepository;
    private final AccountWebClientHelper webHelper;

    @Transactional
    public Worker save(WorkerCreateDto workerCreateDto) {
        Account account = webHelper.saveUser(
                accountMapper.workerCreateDtoToUserCreateDto(workerCreateDto)
        ).block();
        Worker worker = workerMapper.dtoToWorker(workerCreateDto);
        worker.setLandscapeUserId(account.getId());
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
        Account account = webHelper.getUser(worker.getLandscapeUserId()).block();
        return workerMapper.toExtendedWorker(worker, account);
    }

    public List<Worker> findAll() {
        return workerRepository.findAll();
    }

    public List<Worker> findAllByServices(Set<WorkEnum> services) {
        return workerRepository.findAllByServicesContaining(services);
    }

    @Transactional
    public Worker update(WorkerUpdateDto workerUpdateDto) {
        Worker worker = workerMapper.dtoToWorker(workerUpdateDto);
        Worker oldWorker = getById(worker.getId());
        worker.setLandscapeUserId(oldWorker.getLandscapeUserId());
        return workerRepository.save(worker);
    }

    public void delete(String id) {
        workerRepository.deleteById(id);
    }
}
