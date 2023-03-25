package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.landscape.WebClientHelper;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;
import ru.tinkoff.academy.user.User;
import ru.tinkoff.academy.user.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerMapper workerMapper;
    private final UserMapper userMapper;
    private final WorkerRepository workerRepository;
    private final WebClientHelper webHelper;

    @Transactional
    public Worker save(WorkerCreateDto workerCreateDto) {
        User user = webHelper.webClient().post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(this.userMapper.workerCreateDtoToUserCreateDto(workerCreateDto))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class).block();
        Worker worker = this.workerMapper.toWorker(workerCreateDto, user);
        return this.workerRepository.save(worker);
    }

    public ExtendedWorker getById(String id) {
        Worker worker = this.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Worker wasn't find by id: %s", id)));
        User user = webHelper.webClient().get()
                .uri(String.format("/users/%s", worker.getLandscapeId()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class).block();
        return this.workerMapper.toExtendedWorker(worker, user);
    }

    public Optional<Worker> findById(String id) {
        return this.workerRepository.findById(id);
    }

    public List<ExtendedWorker> findAllExtended() {
        return this.findAll().stream().map(this::mapToExtended).toList();
    }

    private ExtendedWorker mapToExtended(Worker worker) {
        User user = webHelper.webClient().get()
                .uri(String.format("/users/%s", worker.getLandscapeId()))
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
            return worker;
        }
        throw new IllegalArgumentException("No entity was update");
    }

    public void delete(String id) {
        this.workerRepository.deleteById(id);
    }
}
