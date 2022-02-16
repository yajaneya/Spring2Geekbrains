package ru.yajaneya.Spring2Geekbrains.api.recoms;

public class BuyProductDto{

    private Long productId;
    private String productName;
    private Integer productQuantity;

    public BuyProductDto() {
    }

    public BuyProductDto(Long productId, String productName, Integer productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
