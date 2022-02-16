package ru.yajaneya.Spring2Geekbrains.recom.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.api.recoms.BuyProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.services.BuyProductsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buyproducts")
@RequiredArgsConstructor
public class BuyProductsController {
    private final BuyProductsService buyProductsService;

    @GetMapping
    public List<BuyProductDto> getCurrentUserOrders() {
        return buyProductsService.findAll();
    }

    @PostMapping
    public void create (@RequestBody List<BuyProductDto> buyProductDtos) {
        buyProductsService.save(buyProductDtos);
    }
}
