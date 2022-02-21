package ru.yajaneya.Spring2Geekbrains.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные по заказу")
public class OrderDetailsDto {
    @Schema(description = "Адрес доставки", maxLength = 255, minLength = 10, example = "685000, Россия, г.Магадан, ул.Пролетарская, 23")
    private String address;
    @Schema(description = "Телефон для связи", maxLength = 25, minLength = 10, example = "+7413-225-11-81")
    private String phone;

    public OrderDetailsDto() {
    }

    public OrderDetailsDto(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
