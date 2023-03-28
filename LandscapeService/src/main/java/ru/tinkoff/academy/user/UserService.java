package ru.tinkoff.academy.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public User save(UserCreateDto userCreateDto) {
        User user = this.userMapper.dtoToUser(userCreateDto);
        return this.userRepository.save(user);
    }

    public User getById(UUID id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User wasn't find by id: %s", id)));
    }

    public Optional<User> findById(UUID id) {
        return this.userRepository.findById(id);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public List<User> findAllById(Iterable<UUID> ids) {
        return this.userRepository.findAllById(ids);
    }

    @Transactional
    public User update(UserUpdateDto userUpdateDto) {
        User user = this.userRepository.getReferenceById(userUpdateDto.getId());
        user = this.userMapper.updateUser(userUpdateDto, user);
        return this.userRepository.save(user);
    }

    public void delete(UUID id) {
        this.userRepository.deleteById(id);
    }
}
