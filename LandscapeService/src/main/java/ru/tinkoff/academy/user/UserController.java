package ru.tinkoff.academy.user;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.academy.user.dto.UserCreateDto;
import ru.tinkoff.academy.user.dto.UserUpdateDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Timed
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public User save(@RequestBody UserCreateDto userCreateDto) {
        return userService.save(userCreateDto);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") UUID id) {
        return userService.getById(id);
    }

    @GetMapping("/all")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("")
    public List<User> findAllById(@RequestParam("ids") List<UUID> ids) {
        return userService.findAllById(ids);
    }

    @PutMapping("")
    public User update(@RequestBody UserUpdateDto userUpdateDto) {
        return userService.update(userUpdateDto);
    }

    @PutMapping("/{id}")
    public User setLatitudeAndLongitude(@PathVariable("id") UUID id, @RequestParam(name = "lat") String latitude,
                                        @RequestParam("lon") String longitude) {
        return userService.updateLatitudeAndLongitude(id, latitude, longitude);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id) {
        this.userService.delete(id);
    }
}
