package ru.yajaneya.Spring2Geekbrains.cart.integretions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yajaneya.Spring2Geekbrains.api.recoms.PutToCartProductDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecomServiceIntegration {
    private final WebClient recomServiceWebClient;

    public void sendProductCartRecom(List<PutToCartProductDto> putToCartProductDtos) {
        recomServiceWebClient.post()
                .uri("/api/v1/puttocartproducts")
                .syncBody(putToCartProductDtos)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }
}
