package ru.yajaneya.Spring2Geekbrains.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yajaneya.Spring2Geekbrains.api.auth.UserDto;
import ru.yajaneya.Spring2Geekbrains.auth.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @GetMapping ("/users")
    public List<UserDto> getUsers () {
        return userService.getUsers();
    }

}
