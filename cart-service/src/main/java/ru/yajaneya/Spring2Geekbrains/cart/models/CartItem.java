package ru.yajaneya.Spring2Geekbrains.cart.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    public CartItem(ProductDto product) {
        this.productId = product.getId();
        this.productTitle = product.getTitle();
        this.quantity = 1;
        this.pricePerProduct = product.getPrice();
        this.price = product.getPrice();
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
        changePrice();
    }

    public void updateProduct(String productName, BigDecimal productPrice) {
        productTitle = productName;
        pricePerProduct = productPrice;
        changePrice();
    }

    private void changePrice() {
        this.price = this.pricePerProduct.multiply(BigDecimal.valueOf(this.quantity));
    }
}
