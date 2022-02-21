package ru.yajaneya.Spring2Geekbrains.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель продукта")
public class ProductDto {

    @Schema(description = "ID продукта", required = true, example = "1")
    private Long id;
    @Schema(description = "Название продукта", required = true, maxLength = 255, minLength = 3, example = "Коробка конфет")
    private String title;
    @Schema(description = "Цена продукта", required = true, example = "120.00")
    private BigDecimal price;
    @Schema(description = "Наименование категории продукта", required = true, maxLength = 255, minLength = 3, example = "Конфеты")
    private String category;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, BigDecimal price, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
