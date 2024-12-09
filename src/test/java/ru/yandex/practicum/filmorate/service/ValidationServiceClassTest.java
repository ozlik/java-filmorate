package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidationServiceClassTest должен: ")
class ValidationServiceClassTest {
    ValidationServiceClass validation = new ValidationServiceClass();
    User user;
    Film film;

    @BeforeEach
    public void init() {
        user = new User();
        film = new Film();
    }

    @Test
    @DisplayName("Выдавать ошибку при создании пользователя с пустым полем логина")
    void shouldNotCreateUserWithoutLogin() {
        user.setEmail("dfkgjg@fdgh.ru");
        user.setBirthday(LocalDate.of(2024, 12, 6));

        user.setLogin("");

        assertThrows(RuntimeException.class, () -> validation.userValidate(user));
    }

    @Test
    @DisplayName("Выдавать ошибку при создании пользователя с пустым полем логина c пробелом")
    void shouldNotCreateUserWithBlankLogin() {
        user.setEmail("dfkgjg@fdgh.ru");
        user.setBirthday(LocalDate.of(2024, 12, 6));

        user.setLogin(" ");

        assertThrows(RuntimeException.class, () -> validation.userValidate(user));
    }

    @Test
    @DisplayName("Выдавать ошибку при создании пользователя с пробелом в логине")
    void shouldNotCreateUserWithEmptySpaceBetweenLogin() {
        user.setEmail("dfkgjg@fdgh.ru");
        user.setBirthday(LocalDate.of(2024, 12, 6));

        user.setLogin("orr rss");

        assertThrows(RuntimeException.class, () -> validation.userValidate(user));
    }

    @Test
    @DisplayName("Выдавать ошибку при создании пользователя с пустым имейлом")
    void shouldNotCreateUserWithOutEmail() {
        user.setLogin("login");
        user.setBirthday(LocalDate.of(2024, 12, 6));

        user.setEmail("");

        assertThrows(RuntimeException.class, () -> validation.userValidate(user));
    }

    @Test
    @DisplayName("Выдавать ошибку при создании пользователя с пустым имейлом с пробелом")
    void shouldNotCreateUserWithBlankEmail() {
        user.setLogin("login");
        user.setBirthday(LocalDate.of(2024, 12, 6));

        user.setEmail(" ");

        assertThrows(RuntimeException.class, () -> validation.userValidate(user));
    }

    @Test
    @DisplayName("Выдавать ошибку при создании пользователя с имейлом без @")
    void shouldNotCreateUserWithOutSymbolEmail() {
        user.setLogin("login");
        user.setBirthday(LocalDate.of(2024, 12, 6));

        user.setEmail("dfkgjg");

        assertThrows(RuntimeException.class, () -> validation.userValidate(user));
    }

    @Test
    @DisplayName("Выдавать ошибку при создании пользователя с датой рождения из будущего")
    void shouldNotCreateUserWithWrongBirthday() {
        user.setEmail("dfkgjg@fdgh.ru");
        user.setLogin("login");

        user.setBirthday(LocalDate.of(2025, 12, 8));

        assertThrows(RuntimeException.class, () -> validation.userValidate(user));
    }

    /*  Фильмы (создание) */
    @Test
    @DisplayName("Выдавать ошибку при создании фильма с пустым полем названия")
    void shouldNotCreateFilmWithoutName() {
        film.setDuration(50);
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895, 12, 29));

        film.setName("");

        assertThrows(RuntimeException.class, () -> validation.filmValidate(film));
    }

    @Test
    @DisplayName("Выдавать ошибку при создании фильма с пустым полем названия c пробелом")
    void shouldNotCreateFilmWithBlankName() {
        film.setDuration(50);
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895, 12, 29));

        film.setName(" ");

        assertThrows(RuntimeException.class, () -> validation.filmValidate(film));
    }

    @Test
    @DisplayName("Выдавать ошибку при создании фильма с датой раньше 28.12.1895")
    void shouldNotCreateFilmWithWrongReleaseDate() {
        film.setName("name");
        film.setDuration(50);
        film.setDescription("description");

        film.setReleaseDate(LocalDate.of(1895, 12, 28));

        assertEquals(LocalDate.of(1895, 12, 28), film.getReleaseDate());
    }

    @Test
    @DisplayName("Выдавать ошибку при создании фильма с датой раньше 28.12.1895")
    void shouldNotCreateFilmWithWrongReleaseDate2() {
        film.setName("name");
        film.setDuration(50);
        film.setDescription("description");

        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        assertThrows(RuntimeException.class, () -> validation.filmValidate(film));
    }

    @Test
    @DisplayName("Создавать фильм с датой позже 28.12.1895")
    void shouldNotCreateFilmWithWrongReleaseDate3() {
        film.setName("name");
        film.setDuration(50);
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895, 12, 29));

        validation.filmValidate(film);

        assertEquals(LocalDate.of(1895, 12, 29), film.getReleaseDate());
    }

    @Test
    @DisplayName("Выдавать ошибку при создании фильма с описание больше 200 символов")
    void shouldNotCreateFilmWithMore200DescriptionLength() {
        film.setName("name");
        film.setDuration(50);
        film.setReleaseDate(LocalDate.of(1895, 12, 29));

        //201
        film.setDescription("Душа моя озарена неземной радостью, как эти чудесные весенние утра, " +
                "которыми я наслаждаюсь от всего сердца. Я совсем один и блаженствую в здешнем краю, " +
                "словно созданном для таких, как я. Я так счастлив");

        assertThrows(RuntimeException.class, () -> validation.filmValidate(film));
    }

    @Test
    @DisplayName("Создавать фильм с описанием строго меньше 200 символов")
    void shouldCreateFilmWith200DescriptionLength() {
        film.setName("name");
        film.setDuration(50);
        film.setReleaseDate(LocalDate.of(1895, 12, 29));

        //200
        film.setDescription("Проснувшись однажды утром после беспокойного сна, Грегор Замза обнаружил, " +
                "что он у себя в постели превратился в страшное насекомое. " +
                "Лежа на панцирнотвердой спине, он видел, стоило ему приподнять голов");

        assertEquals(200, film.getDescription().length());
    }

    @Test
    @DisplayName("Выдавать ошибку при создании фильма с продолжительностью меньше 0")
    void shouldNotCreateFilmWithWrongDuration() {
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895, 12, 29));

        film.setDuration(-50);

        assertThrows(RuntimeException.class, () -> validation.filmValidate(film));
    }

    @Test
    @DisplayName("Создавать фильм с положительной длительностью")
    void shouldCreateFilmWithDuration() {
        film.setName("name");
        film.setDuration(50);
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895, 12, 29));

        film.setDuration(1);
        validation.filmValidate(film);

        assertEquals(1, film.getDuration());
    }

    //обновление пользователя
    @Test
    @DisplayName("Выдавать ошибку при обновлении пользователя с некорректным id")
    void shouldNotUpdateUserWithWrongId() {
        user.setEmail("dfkgjg@fdgh.ru");
        user.setBirthday(LocalDate.of(2024, 12, 6));
        user.setLogin("login");

        assertThrows(RuntimeException.class, () -> validation.userValidateUpdate(user));
    }

    @Test
    @DisplayName("Выдавать ошибку при обновлении пользователя с датой рождения из будущего")
    void shouldNotUpdateUserWithWrongBirthday() {
        user.setEmail("dfkgjg@fdgh.ru");
        user.setLogin("login");
        user.setId(1L);

        user.setBirthday(LocalDate.of(2025, 12, 8));

        assertThrows(RuntimeException.class, () -> validation.userValidateUpdate(user));
    }
}