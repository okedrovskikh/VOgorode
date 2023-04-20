package ru.tinkoff.academy.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User save(UserCreateDto createDto) {
        User user = userMapper.dtoToUser(createDto);
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User wasn't find by id: %s", id)));
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User getByEmailOrTelephone(String email, String telephone) {
        return findByEmailAndTelephone(email, telephone)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User wasn't find by email=%S or telephone%S", email, telephone)));
    }

    public Optional<User> findByEmailAndTelephone(String email, String telephone) {
        return userRepository.findByEmailOrTelephone(email, telephone);
    }

    public List<User> findAll(boolean sortedBySurname) {
        return sortedBySurname ? findAllSortedBySurname() : findAllUnsorted();
    }

    public List<User> findAllUnsorted() {
        return userRepository.findAll();
    }

    public List<User> findAllSortedBySurname() {
        return userRepository.findAll(Sort.by("surname"));
    }

    public User update(UserUpdateDto updateDto) {
        User user = userRepository.getReferenceById(updateDto.getId());
        user = userMapper.updateUser(user, updateDto);
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
