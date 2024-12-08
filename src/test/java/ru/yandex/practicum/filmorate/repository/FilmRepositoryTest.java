package ru.yandex.practicum.filmorate.repository;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.filmorate.model.Film;

class FilmRepositoryTest {
    FilmRepository repository;
    Film film;

    @BeforeEach
    public void init() {
        film = new Film();
        repository = new FilmRepository();
    }

}