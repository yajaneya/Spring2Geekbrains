package ru.yajaneya.Spring2Geekbrains.api.recoms;

public class RecomProductDto {

    private Long productId;
    private String productName;
    private Integer productQuantity;
    private String type;

    public RecomProductDto() {
    }

    public RecomProductDto(Long productId, String productName, Integer productQuantity, String type) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
