package ru.yajaneya.Spring2Geekbrains.api.recoms;

public class PutToCartProductDto {

    private String productName;
    private Integer productQuantity;

    public PutToCartProductDto() {
    }

    public PutToCartProductDto(String productName, Integer productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}