package ru.yajaneya.Spring2Geekbrains.cart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import ru.yajaneya.Spring2Geekbrains.cart.models.Cart;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CartControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void uuidTest() {

        // Проверка формирования uuid корзины
        String uuid = restTemplate.getForObject("/api/v1/cart/generate", String.class);
        assertThat(uuid).isNotNull();
        assertThat(uuid).isNotEmpty();

        // Проверка создания временной корзины по uuid
        Cart cart = restTemplate.getForObject("/api/v1/cart/qqq", Cart.class);
        assertThat(cart).isNotNull();
        assertThat(cart).isEqualTo(new Cart());

    }
}
