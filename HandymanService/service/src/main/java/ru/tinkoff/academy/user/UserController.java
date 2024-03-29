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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Timed(
        value = "business.request.duration",
        extraTags = {"process", "user"},
        description = "Duration Handyman service User process handling",
        histogram = true
)
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public User save(@RequestBody UserCreateDto createDto) {
        return userService.save(createDto);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") String id) {
        return userService.getById(id);
    }

    @GetMapping("/all")
    public List<User> findAll(@RequestParam(name = "sort", defaultValue = "false") boolean sortedBySurname) {
        return userService.findAll(sortedBySurname);
    }

    @PutMapping("")
    public User update(@RequestBody UserUpdateDto updateDto) {
        return userService.update(updateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        userService.delete(id);
    }
}
