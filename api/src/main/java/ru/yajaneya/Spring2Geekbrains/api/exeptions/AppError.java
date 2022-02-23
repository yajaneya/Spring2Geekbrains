package ru.yajaneya.Spring2Geekbrains.api.exeptions;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ошибка в приложении")
public class AppError {
    @Schema(description = "Код ошибки", required = true, example = "RESOURCE_NOT_FOUND_EXCEPTION")
    private String code;
    @Schema(description = "Сообщение об ошибке", required = true, example = "Продукт с id = 1 не найден.")
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppError() {
    }

    public AppError(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
