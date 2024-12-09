package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class User {
    private Long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}
