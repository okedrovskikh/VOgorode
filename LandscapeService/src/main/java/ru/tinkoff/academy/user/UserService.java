package ru.tinkoff.academy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public User save(UserCreateDto userCreateDto) {
        User user = this.userMapper.dtoToUser(userCreateDto);
        return this.userRepository.save(user);
    }

    public User getById(Long id) {
        return this.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User wasn't find by id: %s", id)));
    }

    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    public List<User> findAllById(Iterable<Long> ids) {
        return this.userRepository.findAllById(ids);
    }

    @Transactional
    public User update(UserUpdateDto userUpdateDto) {
        User user = this.userMapper.dtoToUser(userUpdateDto);
        if (this.userRepository.update(user) == 1) {
            return user;
        }
        throw new IllegalArgumentException("No user was update");
    }

    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }
}
