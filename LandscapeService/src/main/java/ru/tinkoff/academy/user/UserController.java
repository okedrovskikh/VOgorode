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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public User save(@RequestBody UserCreateDto userCreateDto) {
        return this.userService.save(userCreateDto);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        return this.userService.getById(id);
    }

    @PutMapping("")
    public User update(@RequestBody UserUpdateDto userUpdateDto) {
        return this.userService.update(userUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.userService.delete(id);
    }
}
