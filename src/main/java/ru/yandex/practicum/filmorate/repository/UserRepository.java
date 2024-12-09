package ru.yandex.practicum.filmorate.repository;

import lombok.Getter;
import ru.yandex.practicum.filmorate.exception.DuplicatedDataException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;

@Getter
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();

    public void saveUser(User user) {
        if (users.values().stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new DuplicatedDataException("Этот имейл уже используется");
        }
        if ((user.getName() == null) || (user.getName().isBlank())) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        users.put(user.getId(), user);
    }

    public User updateUser(User newUser) {
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            if (!(newUser.getEmail() == null) && (newUser.getEmail().isBlank())) {
                if (users.values().stream()
                        .anyMatch(u -> u.getEmail().equals(newUser.getEmail()))) {
                    throw new DuplicatedDataException("Этот имейл уже используется");
                }
                oldUser.setEmail(newUser.getEmail());
            }
            if (newUser.getBirthday() != null) {
                oldUser.setBirthday(newUser.getBirthday());
            }
            if (!(newUser.getLogin() == null) && ((newUser.getName() == null) || (newUser.getName().isBlank()))
                    && oldUser.getName().equals(oldUser.getLogin())) {
                oldUser.setName(newUser.getLogin());
            }
            if (!(newUser.getLogin() == null)) {
                oldUser.setLogin(newUser.getLogin());
            }
            if (!(newUser.getName() == null) || !(newUser.getName().isBlank())) {
                oldUser.setName(newUser.getName());
            }
            return oldUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
