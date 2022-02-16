package ru.yajaneya.Spring2Geekbrains.core.integretions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.yajaneya.Spring2Geekbrains.api.recoms.BuyProductDto;
import ru.yajaneya.Spring2Geekbrains.api.recoms.PutToCartProductDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecomServiceIntegration {
    private final WebClient recomServiceWebClient;

    public void sendProductRecom(List<BuyProductDto> buyProductDtos) {
        recomServiceWebClient.post()
                .uri("/api/v1/buyproducts")
                .syncBody(buyProductDtos)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

    public void sendProductCartRecom(List<PutToCartProductDto> putToCartProductDtos) {
        recomServiceWebClient.post()
                .uri("/api/v1/puttocartproducts")
                .syncBody(putToCartProductDtos)
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }
}
