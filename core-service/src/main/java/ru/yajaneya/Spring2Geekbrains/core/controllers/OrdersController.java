package ru.yajaneya.Spring2Geekbrains.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.core.converters.OrderConverter;
import ru.yajaneya.Spring2Geekbrains.core.dto.OrderDetailsDto;
import ru.yajaneya.Spring2Geekbrains.core.dto.OrderDto;
import ru.yajaneya.Spring2Geekbrains.core.services.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;
    private final OrderConverter orderConverter;


    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return ordersService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        ordersService.createOrder(username, orderDetailsDto);
    }
}
