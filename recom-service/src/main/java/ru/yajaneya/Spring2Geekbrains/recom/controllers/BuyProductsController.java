package ru.yajaneya.Spring2Geekbrains.recom.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.api.recoms.BuyProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.BuyProduct;
import ru.yajaneya.Spring2Geekbrains.recom.services.BuyProductsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buyproducts")
@RequiredArgsConstructor
public class BuyProductsController {
    private final BuyProductsService buyProductsService;

    @GetMapping
    public List<BuyProductDto> getCurrentUserOrders() {
        List<BuyProductDto> s = buyProductsService.findAll();
        s.forEach(q -> System.out.println(q));
        return s;
    }

    @PostMapping
    public BuyProduct create (@RequestBody BuyProduct buyProduct) {
        return buyProductsService.save(buyProduct);
    }
}
