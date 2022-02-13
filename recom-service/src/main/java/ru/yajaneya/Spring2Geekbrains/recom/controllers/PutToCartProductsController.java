package ru.yajaneya.Spring2Geekbrains.recom.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.api.recoms.PutToCartProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.entities.PutToCartProduct;
import ru.yajaneya.Spring2Geekbrains.recom.services.PutToCartProductsService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/puttocartproducts")
@RequiredArgsConstructor
public class PutToCartProductsController {
    private final PutToCartProductsService putToCartProductsService;

    @GetMapping
    public List<PutToCartProductDto> getCurrentUserOrders() {
        return putToCartProductsService.findAll();
    }

    @PostMapping
    public PutToCartProduct create (@RequestBody PutToCartProduct buyProduct) {
        buyProduct.setId(null);
        return putToCartProductsService.save(buyProduct);
    }
}
