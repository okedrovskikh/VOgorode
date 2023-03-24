package ru.tinkoff.academy.worker;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.academy.worker.dto.WorkerCreateDto;
import ru.tinkoff.academy.worker.dto.WorkerUpdateDto;
import ru.tinkoff.academy.user.User;
import ru.tinkoff.academy.user.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerService {
    private final WorkerMapper workerMapper;
    private final UserMapper userMapper;
    private final WorkerRepository workerRepository;
    private final String uri = "http://localhost:8080";

    public Worker save(WorkerCreateDto workerCreateDto) {
        Worker worker = this.workerMapper.dtoToWorker(workerCreateDto);
        WebClient webClient = WebClient.create(this.uri);
        Mono<User> user = webClient.post().uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(this.userMapper.workerCreateDtoToUserCreateDto(workerCreateDto))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class);
        user.block();
        return this.workerRepository.save(worker);
    }

    public ExtendedWorker getById(Long id) {
        Worker worker = this.findById(id).orElseThrow(() -> new IllegalArgumentException());
        WebClient client = WebClient.create(this.uri);
        Mono<User> user = client.get().uri(String.format("/users/%s", id))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(User.class);
        return this.workerMapper.toExtendedWorker(worker, user.block());
    }

    public Optional<Worker> findById(Long id) {
        return this.workerRepository.findById(id);
    }

    public Worker update(WorkerUpdateDto workerUpdateDto) {
        Worker worker = this.workerMapper.dtoToWorker(workerUpdateDto);
        if (this.workerRepository.update(worker) == 1) {
            return worker;
        }
        throw new IllegalArgumentException();
    }

    public void delete(Long id) {
        this.workerRepository.deleteById(id);
    }
}
