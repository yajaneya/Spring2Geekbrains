package ru.yajaneya.Spring2Geekbrains.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель тектового ответа")
public class StringResponse {
    @Schema(description = "Содержание ответа", required = true, example = "7e7fd6b6-ba92-468d-b01a-e1d187f9d31f")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public StringResponse(String value) {
        this.value = value;
    }

    public StringResponse() {
    }
}
