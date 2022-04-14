package ru.yajaneya.Spring2Geekbrains.api.core;

import java.util.List;

public class CategoryDto {

    private Long id;
    private String categoryName;
    private List<ProductDto> productDtos;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String categoryName, List<ProductDto> productDtos) {
        this.id = id;
        this.categoryName = categoryName;
        this.productDtos = productDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductDto> getProductDtos() {
        return productDtos;
    }

    public void setProductDtos(List<ProductDto> productDtos) {
        this.productDtos = productDtos;
    }
}
