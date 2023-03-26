package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.landscape.WebClientHelper;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;
import ru.tinkoff.academy.user.User;
import ru.tinkoff.academy.user.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerMapper workerMapper;
    private final UserMapper userMapper;
    private final WorkerRepository workerRepository;
    private final WebClientHelper webHelper;

    @Transactional
    public Worker save(WorkerCreateDto workerCreateDto) {
        Worker worker = this.workerMapper.dtoToWorker(workerCreateDto);
        worker = this.workerRepository.save(worker);
        this.webHelper.webClient().post()
                .uri(String.format("/users/%s", worker.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(this.userMapper.workerCreateDtoToUserCreateDto(workerCreateDto))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class).block();
        return worker;
    }

    public ExtendedWorker getById(UUID id) {
        Worker worker = this.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Worker wasn't find by id: %s", id)));
        User user = this.webHelper.webClient().get()
                .uri(String.format("/users/%s", worker.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class).block();
        return this.workerMapper.toExtendedWorker(worker, user);
    }

    public Optional<Worker> findById(UUID id) {
        return this.workerRepository.findById(id);
    }

    public List<ExtendedWorker> findAllExtended() {
        return this.findAll().stream().map(this::mapToExtended).toList();
    }

    private ExtendedWorker mapToExtended(Worker worker) {
        User user = this.webHelper.webClient().get()
                .uri(String.format("/users/%s", worker.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class).block();
        return this.workerMapper.toExtendedWorker(worker, user);
    }

    public List<Worker> findAll() {
        return this.workerRepository.findAll();
    }

    @Transactional
    public Worker update(WorkerUpdateDto workerUpdateDto) {
        Worker worker = this.workerMapper.dtoToWorker(workerUpdateDto);
        if (this.workerRepository.updateById(worker.getId(), worker) == 1) {
            this.webHelper.webClient().put()
                    .uri("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(this.userMapper.workerUpdateDtoToUserUpdateDto(workerUpdateDto))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class).block();
            return worker;
        }
        throw new IllegalArgumentException("No entity was update");
    }

    public void delete(UUID id) {
        this.workerRepository.deleteById(id);
    }
}
