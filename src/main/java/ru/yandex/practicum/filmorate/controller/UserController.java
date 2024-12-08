package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.service.ValidationServiceClass;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    final UserRepository repository;
    final ValidationServiceClass validation;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<User> findAll() {
        return repository.getUsers().values();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        log.info("Create User: {} - Started", user);
        // проверяем выполнение необходимых условий
        validation.userValidate(user);
        //сохраняем в репозитории
        repository.saveUser(user);
        log.info("Create User: {} - Finished", user);
        return user;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody User newUser) {
        log.info("Update User: {} - Started", newUser);
        // проверяем необходимые условия
        validation.userValidateUpdate(newUser);
        // если пользователь найден и все условия соблюдены, обновляем его
        User oldUser = repository.updateUser(newUser);
        log.info("Update User: {} - Finished", oldUser);
        return oldUser;
    }
}

