package ru.yandex.practicum.filmorate.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    UserRepository repository;
    User user;

    @BeforeEach
    public void init() {
        user = new User();
        repository = new UserRepository();
    }

    @Test
    @DisplayName("Если поле имени при создании не заполнено пользователем, заполнять логином его")
    void shouldSetLoginAsNameIfNameIsEmpty() {
        user.setName("");
        user.setLogin("login");

        repository.saveUser(user);

        assertEquals(user.getName(), user.getLogin());
    }


}