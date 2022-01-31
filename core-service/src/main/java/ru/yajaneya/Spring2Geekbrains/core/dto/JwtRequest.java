package ru.yajaneya.Spring2Geekbrains.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequest {
    private String username;
    private String password;
}
