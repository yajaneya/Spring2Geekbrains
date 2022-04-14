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
//    private Long categoryId;
    @Schema(description = "Наименование категории продукта", required = true, maxLength = 255, minLength = 3, example = "Конфеты")
//    private String categoryName;
    private CategoryDto categoryDto;

    public ProductDto() {
    }

//    public ProductDto(Long id, String title, BigDecimal price, Long CategoryId, String categoryName) {
    public ProductDto(Long id, String title, BigDecimal price, CategoryDto categoryDto) {
        this.id = id;
        this.title = title;
        this.price = price;
//        this.categoryId = categoryId;
//        this.categoryName = categoryName;
        this.categoryDto = categoryDto;
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

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    //    public Long getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Long categoryId) {
//        this.categoryId = categoryId;
//    }
//
//    public String getCategoryName() {
//        return categoryName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        this.categoryName = categoryName;
//    }
}
