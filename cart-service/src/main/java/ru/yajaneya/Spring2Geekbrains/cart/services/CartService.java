package ru.yajaneya.Spring2Geekbrains.cart.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.api.recoms.RecomProductDto;
import ru.yajaneya.Spring2Geekbrains.cart.integretions.ProductsServiceIntegration;
import ru.yajaneya.Spring2Geekbrains.cart.integretions.RecomServiceIntegration;
import ru.yajaneya.Spring2Geekbrains.cart.models.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductsServiceIntegration productsServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RecomServiceIntegration recomServiceIntegration;

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    @Value("${constant.recom-send}")
    private int timePause;
    private List<RecomProductDto> recomProductDtos = new ArrayList<>();
    private Date saveDate = new Date();

    public void getCarts () {
        redisTemplate.getClientList();
    }


    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }

    public void addToCart(String cartKey, Long productId) {
        ProductDto productDto = productsServiceIntegration.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
        execute(cartKey, c -> {
            if (!c.add(productId)) {
                c.add(productDto);
            }
        });
        for (int i=0; i<recomProductDtos.size(); i++) {
            RecomProductDto p = recomProductDtos.get(i);
            if (p.getProductId().equals(productDto.getId())) {
                p.setProductQuantity(p.getProductQuantity()+1);
                return;
            }
        }
        RecomProductDto recomProductDto = new RecomProductDto();
        recomProductDto.setProductId(productDto.getId());
        recomProductDto.setProductName(productDto.getTitle());
        recomProductDto.setProductQuantity(1);
        recomProductDto.setType("cart");
        recomProductDtos.add(recomProductDto);
        Date date = new Date();
        if ((date.getTime() - saveDate.getTime()) > timePause) {
            saveDate = date;
            //отправка данных в реком-сервис
            recomServiceIntegration.sendProductCartRecom(recomProductDtos);
            recomProductDtos.clear();
        }
    }

    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }

    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }

    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }

    public void updateNameProduct(String cartKey, Long productId, String productName, BigDecimal productPrice) {
        execute(cartKey, c -> c.update(productId, productName, productPrice));
    }

    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }

    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}
