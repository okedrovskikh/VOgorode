package ru.tinkoff.academy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(UserCreateDto userCreateDto) {
        return null;
    }

    public User getById(Long id) {
        return null;
    }

    public User update(UserUpdateDto userUpdateDto) {
        return null;
    }

    public void delete(Long id) {

    }
}
