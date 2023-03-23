package ru.tinkoff.academy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public User save(UserCreateDto userCreateDto) {
        User user = this.userMapper.dtoToUser(userCreateDto);
        return user;
    }

    public User getById(Long id) {
        return null;
    }

    public User update(UserUpdateDto userUpdateDto) {
        User user = this.userMapper.dtoToUser(userUpdateDto);
        return user;
    }

    public void delete(Long id) {

    }
}
