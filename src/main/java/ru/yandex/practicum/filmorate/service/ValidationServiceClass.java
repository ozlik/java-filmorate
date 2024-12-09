package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

@Service
public class ValidationServiceClass implements ValidationService {

    @Override
    public void filmValidate(Film film) {
        if (film.getName() == null || film.getName().trim().isEmpty()) {
            throw new ConditionsNotMetException("Название не может быть пустым");
        }
        if (film.getDuration() <= 0) {
            throw new ConditionsNotMetException("Продолжительность фильма должна быть положительным числом");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ConditionsNotMetException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
        if (film.getDescription().length() > 200) {
            throw new ConditionsNotMetException("Максимальная длина описания — 200 символов");
        }
    }

    @Override
    public void filmValidateUpdate(Film newFilm) {
        if (newFilm.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if (newFilm.getDescription() == null || newFilm.getDescription().trim().isEmpty()) {
            throw new ConditionsNotMetException("Описание не может быть пустым");
        }
        if (newFilm.getDescription().length() <= 200) {
            throw new ConditionsNotMetException("Максимальная длина описания — 200 символов");
        }
        if (newFilm.getDuration() <= 0) {
            throw new ConditionsNotMetException("Продолжительность фильма должна быть положительным числом");
        }
        if (newFilm.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ConditionsNotMetException("Дата релиза должна быть не раньше 28 декабря 1895 года");
        }
    }

    @Override
    public void userValidate(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new ConditionsNotMetException("Имейл должен быть указан");
        }
        if (!user.getEmail().contains("@")) {
            throw new ConditionsNotMetException("Имейл должен содержать символ @");
        }

        if (user.getLogin() == null || user.getLogin().trim().isEmpty()) {
            throw new ConditionsNotMetException("Логин не может быть пустым");
        }
        if (user.getLogin().contains(" ")) {
            throw new ConditionsNotMetException("В логине не должно быть пробелов");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ConditionsNotMetException("Дата рождения не может быть в будущем");
        }
    }

    @Override
    public void userValidateUpdate(User newUser) {
        if (newUser.getId() == null) {
            throw new ConditionsNotMetException("Id должен быть указан");
        }
        if ((newUser.getEmail() == null || newUser.getEmail().trim().isEmpty())
                && (newUser.getLogin() == null || newUser.getLogin().trim().isEmpty())
                && (newUser.getBirthday() == null)) {
            throw new ConditionsNotMetException("Все поля пусты");
        }
        if (newUser.getEmail() != null || !newUser.getEmail().trim().isEmpty()) {
            if (!newUser.getEmail().contains("@")) {
                throw new ConditionsNotMetException("Имейл должен содержать символ @");
            }
        }
        if (newUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ConditionsNotMetException("Дата рождения не может быть в будущем");
        }
        if (newUser.getLogin().contains(" ")) {
            throw new ConditionsNotMetException("В логине не должно быть пробелов");
        }
    }
}
