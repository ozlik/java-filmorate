package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

public interface ValidationService {
    void filmValidate(Film film);

    void filmValidateUpdate(Film film);

    void userValidate(User user);

    void userValidateUpdate(User user);
}
