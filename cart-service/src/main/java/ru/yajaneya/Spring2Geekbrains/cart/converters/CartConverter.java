package ru.yajaneya.Spring2Geekbrains.cart.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yajaneya.Spring2Geekbrains.api.carts.CartDto;
import ru.yajaneya.Spring2Geekbrains.api.carts.CartItemDto;
import ru.yajaneya.Spring2Geekbrains.cart.models.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {

    public CartDto modelToDto (Cart cart) {
        List<CartItemDto> cartItemDtos = cart.getItems().stream().map(it ->
                new CartItemDto(
                        it.getProductId(),
                        it.getProductTitle(),
                        it.getQuantity(),
                        it.getPricePerProduct(),
                        it.getPrice())
        ).collect(Collectors.toList());
        CartDto cartDto = new CartDto(cartItemDtos, cart.getTotalPrice());
        return cartDto;
    }

}
