package ru.yajaneya.Spring2Geekbrains.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.yajaneya.Spring2Geekbrains.core.entities.Category;
import ru.yajaneya.Spring2Geekbrains.core.entities.Product;
import ru.yajaneya.Spring2Geekbrains.core.services.CartService;
import ru.yajaneya.Spring2Geekbrains.core.services.ProductsService;

import java.util.Optional;

@SpringBootTest
public class CartTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductsService productsService;

    @BeforeEach
    public void initCart() {
        cartService.clearCart("test_cart");
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Milk");
        product1.setPrice(50);
        Category category = new Category();
        category.setId(1L);
        category.setCategoryName("Milks");
        product1.setCategory(category);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Cream");
        product2.setPrice(150);
        product2.setCategory(category);
        Mockito.doReturn(Optional.of(product1)).when(productsService).findByID(1L);
        Mockito.doReturn(Optional.of(product2)).when(productsService).findByID(2L);
    }

    @Test
    public void addToCartTest() {

        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 1L);
        cartService.addToCart("test_cart", 2L);
        cartService.addToCart("test_cart", 2L);

        // Проверка перехода корзины в состояние добавления продукта и увеличения количества продукта
        Mockito.verify(productsService, Mockito.times(1)).findByID(ArgumentMatchers.eq(1L));
        Mockito.verify(productsService, Mockito.times(1)).findByID(ArgumentMatchers.eq(2L));

        // Проверка размещения нужного количества продуктов в корзине
        Assertions.assertEquals(2, cartService.getCurrentCart("test_cart").getItems().size());

        // Проверка правильного рассчета общей стоимости корзины
        Assertions.assertEquals(450, cartService.getCurrentCart("test_cart").getTotalPrice());
    }

}