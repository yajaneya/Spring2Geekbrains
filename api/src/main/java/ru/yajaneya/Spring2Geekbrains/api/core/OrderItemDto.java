package ru.yajaneya.Spring2Geekbrains.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель позиции продукта в заказе")
public class OrderItemDto {
    @Schema(description = "ID продукта", required = true, example = "1")
    private Long productId;
    @Schema(description = "Название продукта", required = true, example = "Корбка конфет")
    private String productTitle;
    @Schema(description = "Количество продукта", required = true, example = "3")
    private int quantity;
    @Schema(description = "Цена продукта", required = true, example = "120.00")
    private BigDecimal pricePerProduct;
    @Schema(description = "Стоимость продуктов", required = true, example = "360.00")
    private BigDecimal price;

    public OrderItemDto() {
    }

    public OrderItemDto(Long productId, String productTitle, int quantity, BigDecimal pricePerProduct, BigDecimal price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
