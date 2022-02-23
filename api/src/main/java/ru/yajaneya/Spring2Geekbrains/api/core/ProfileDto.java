package ru.yajaneya.Spring2Geekbrains.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель профиля пользователя")
public class ProfileDto {
    @Schema(description = "Имя пользователя", required = true, maxLength = 255, minLength = 3, example = "Vasiliy")
    private String username;

    public ProfileDto() {
    }

    public ProfileDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
