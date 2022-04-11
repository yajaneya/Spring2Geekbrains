package ru.yajaneya.Spring2Geekbrains.api.auth;

public class UserDto {
    private String username;

    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
