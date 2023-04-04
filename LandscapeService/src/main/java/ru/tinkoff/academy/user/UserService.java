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
        User user = userMapper.dtoToUser(userCreateDto);
        return userRepository.save(user);
    }

    public User getById(UUID id) {
        return this.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User wasn't find by id: %s", id)));
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllById(Iterable<UUID> ids) {
        return userRepository.findAllById(ids);
    }

    @Transactional
    public User update(UserUpdateDto userUpdateDto) {
        User user = userRepository.getReferenceById(userUpdateDto.getId());
        user = userMapper.updateUser(userUpdateDto, user);
        return userRepository.save(user);
    }

    public User updateLatitudeAndLongitude(UUID id, String latitude, String longitude) {
        User user = userRepository.getReferenceById(id);
        user.setLatitude(Double.valueOf(latitude));
        user.setLongitude(Double.valueOf(longitude));
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
}
