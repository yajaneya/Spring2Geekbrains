package ru.yajaneya.Spring2Geekbrains.cart.integretions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yajaneya.Spring2Geekbrains.api.core.ProductDto;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.CartServiceAppError;
import ru.yajaneya.Spring2Geekbrains.api.exeptions.CoreServiceAppError;
import ru.yajaneya.Spring2Geekbrains.cart.exceptions.CoreServiceIntegrationException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductsServiceIntegration {
    private final WebClient coreServiceWebClient;

    public Optional<ProductDto> findById(Long id) {

        ProductDto productDto = coreServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(CartServiceAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(CoreServiceAppError.CoreServiceErrors.PRODUCT_NOT_FOUND.name())) {
                                        return new CoreServiceIntegrationException("Выполнен некорректный запрос к core-сервису: продукт не найден");
                                    }
                                    if (body.getCode().equals(CoreServiceAppError.CoreServiceErrors.CART_SERVICE_INTEGRATION_ERROR.name())) {
                                        return new CoreServiceIntegrationException("Выполнен некорректный запрос к core-сервису: нет связи");
                                    }
                                    return new CoreServiceIntegrationException("Выполнен некорректный запрос к core-сервису: причина неизвестна");
                                }
                        )
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CoreServiceIntegrationException("Core-сервис сломался")))
                .bodyToMono(ProductDto.class)
                .block();
        return Optional.ofNullable(productDto);

    }
}
