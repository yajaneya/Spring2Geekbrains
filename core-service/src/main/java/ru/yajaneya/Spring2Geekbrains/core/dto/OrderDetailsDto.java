package ru.yajaneya.Spring2Geekbrains.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailsDto {
    private String address;
    private String phone;
}
