package ru.yajaneya.Spring2Geekbrains.cart.integretions;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.ResourceNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String productServiceUrl;

    public Optional<ProductDto> findById(Long id) {
        try {
            ProductDto productDto = restTemplate.getForObject(
                    productServiceUrl + "/api/v1/products/" + id, ProductDto.class);
            return Optional.ofNullable(productDto);
        } catch (HttpServerErrorException e) {
            throw new ResourceNotFoundException("Невозможно добавить продукт в корзину. Не доступен сервис продуктов.");
        } catch (HttpClientErrorException e) {
            throw new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт с id = " + id + " не найден.");
        }
    }
}
