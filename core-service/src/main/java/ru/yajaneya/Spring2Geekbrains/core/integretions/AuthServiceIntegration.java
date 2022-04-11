package ru.yajaneya.Spring2Geekbrains.core.integretions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthServiceIntegration {
    private final WebClient authServiceWebClient;

    public List getUsers() {
       return authServiceWebClient.get()
                .uri("/users")
                .retrieve()
                .bodyToMono(List.class)
                .block();
    }

}
