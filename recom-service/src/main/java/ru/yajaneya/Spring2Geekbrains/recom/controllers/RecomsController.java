package ru.yajaneya.Spring2Geekbrains.recom.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.api.recoms.RecomProductDto;
import ru.yajaneya.Spring2Geekbrains.recom.services.RecomServiceFabric;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recoms")
@RequiredArgsConstructor
public class RecomsController {
    private final RecomServiceFabric recomServiceFabric;

    @GetMapping
    public List<RecomProductDto> getCurrentUserOrders(@RequestParam String type) {
        System.out.println("BuyProductsController -> getCurrentUserOrders");
        return recomServiceFabric.getRecomeService(type).findAll();
    }

    @PostMapping
    public void create (@RequestBody List<RecomProductDto> recomProductDtos) {
        System.out.println("BuyProductsController -> create");
        String type = recomProductDtos.get(0).getType();
        recomServiceFabric.getRecomeService(type).save(recomProductDtos);
    }

}
