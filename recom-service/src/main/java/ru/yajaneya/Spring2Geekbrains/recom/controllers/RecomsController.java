package ru.yajaneya.Spring2Geekbrains.recom.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recoms")
@RequiredArgsConstructor
public class RecomsController {

    @GetMapping ("/buy")
    public String getRecomFiveMoreBuyProducts() {
        return "Рекомендация: 5 наиболее покупаемых продуктов за месяц";
    }

    @GetMapping ("/cart")
    public String getRecomFiveMoreToCartProducts() {
        return "Рекомендации 5 наиболее складываемых в корзину продуктов за день";
    }

}
