package ru.yajaneya.Spring2Geekbrains.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.cart.integretions.ProductsServiceIntegration;
import ru.yajaneya.Spring2Geekbrains.cart.services.CartService;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
public class CartTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductsServiceIntegration productsService;

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
        ProductDto product1 = new ProductDto();
        product1.setId(1L);
        product1.setTitle("Milk");
        product1.setPrice(BigDecimal.valueOf(50.00));
        product1.setCategory("Milk");
        ProductDto product2 = new ProductDto();
        product2.setId(2L);
        product2.setTitle("Cream");
        product2.setPrice(BigDecimal.valueOf(150.00));
        product2.setCategory("Milk");
        Mockito.doReturn(Optional.of(product1)).when(productsService).findById(1L);
        Mockito.doReturn(Optional.of(product2)).when(productsService).findById(2L);
    }

    @Test
    public void addToCartTest() {

        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 2L);
        cartService.addToCart("test_cart", 2L);

        // Проверка перехода корзины в состояние добавления продукта и увеличения количества продукта
        Mockito.verify(productsService, Mockito.times(1)).findById(ArgumentMatchers.eq(1L));
        Mockito.verify(productsService, Mockito.times(1)).findById(ArgumentMatchers.eq(2L));

        // Проверка размещения нужного количества продуктов в корзине
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());

        // Проверка правильного рассчета общей стоимости корзины
        Assertions.assertEquals(450, cartService.getCurrentCart("test_cart").getTotalPrice());
    }

}