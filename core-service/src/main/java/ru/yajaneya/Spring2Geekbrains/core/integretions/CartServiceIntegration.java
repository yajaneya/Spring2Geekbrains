package ru.yajaneya.Spring2Geekbrains.core.integretions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yajaneya.Spring2Geekbrains.api.carts.CartDto;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.CartServiceAppError;
import ru.yajaneya.Spring2Geekbrains.core.Observer;
import ru.yajaneya.Spring2Geekbrains.core.Publisher;
import ru.yajaneya.Spring2Geekbrains.core.exceptions.CartServiceIntegrationException;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration implements Observer {
    private final WebClient cartServiceWebClient;
    private final AuthServiceIntegration authServiceIntegration;

    public void clearUserCart(String username) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/0/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public CartDto getUserCart(String username) {
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CartServiceAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_NOT_FOUND.name())) {
                                        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина не найдена");
                                    }
                                    if (body.getCode().equals(CartServiceAppError.CartServiceErrors.CART_IS_BROKEN.name())) {
                                        return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина сломана");
                                    }
                                    return new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: причина неизвестна");
                                }
                        )
                )
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new CartServiceIntegrationException("Выполнен некорректный запрос к сервису корзин")))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CartServiceIntegrationException("Сервис корзин сломался")))
                .bodyToMono(CartDto.class)
                .block();
    }

    public void updateProduct (String username, Long productId, String productName, BigDecimal productPrice) {
        cartServiceWebClient.get()
                .uri("/api/v1/cart/0/update/" + productId + "/" + productName + "/" + productPrice)
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public void update(Publisher publisher, Object object) {
        List users = authServiceIntegration.getUsers();

        ProductDto productDto = (ProductDto) object;
        for (Object user : users) {
            LinkedHashMap<String, String> us = (LinkedHashMap<String, String>) user;
            updateProduct(us.get("username"),
                    productDto.getId(),
                    productDto.getTitle(),
                    productDto.getPrice());
        }
    }
}
