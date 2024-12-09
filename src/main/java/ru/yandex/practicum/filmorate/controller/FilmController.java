package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.service.ValidationServiceClass;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
@Slf4j
public class FilmController {


    final FilmRepository repository;
    final ValidationServiceClass validation;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Film> findAll() {
        return repository.getFilms().values();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film create(@RequestBody Film film) {
        log.info("Create Film: {} - Started", film);
        // проверяем выполнение необходимых условий
        validation.filmValidate(film);
        // сохраняем фильм в репозитории
        repository.saveFilm(film);
        log.info("Create Film: {} - Finished", film);
        return film;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Film update(@RequestBody Film newFilm) {
        log.info("Update Film: {} - Started", newFilm);
        // проверяем необходимые условия
        validation.filmValidateUpdate(newFilm);
        // если фильм найден и все условия соблюдены
        Film oldFilm = repository.updateFilm(newFilm);
        log.info("Update Film: {} - Finished", oldFilm);
        return oldFilm;
    }
}

