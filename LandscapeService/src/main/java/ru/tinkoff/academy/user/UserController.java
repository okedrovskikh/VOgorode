package ru.tinkoff.academy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public User save(@RequestBody UserCreateDto userCreateDto) {
        return this.userService.save(userCreateDto);
    }

    @PostMapping("/{id}")
    public User save(@PathVariable("id") UUID id, @RequestBody UserCreateDto userCreateDto) {
        return this.userService.save(id, userCreateDto);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") UUID id) {
        return this.userService.getById(id);
    }

    @GetMapping("")
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @GetMapping("/{ids}")
    public List<User> findAllById(@PathVariable("ids") List<UUID> ids) {
        return this.userService.findAllById(ids);
    }

    @PutMapping("")
    public User update(@RequestBody UserUpdateDto userUpdateDto) {
        return this.userService.update(userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        this.userService.delete(id);
    }
}
