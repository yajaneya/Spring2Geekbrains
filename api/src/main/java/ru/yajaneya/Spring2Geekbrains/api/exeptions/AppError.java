package ru.yajaneya.Spring2Geekbrains.api.exeptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppError {
    private String message;
    private int statusCode;

    public AppError(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
