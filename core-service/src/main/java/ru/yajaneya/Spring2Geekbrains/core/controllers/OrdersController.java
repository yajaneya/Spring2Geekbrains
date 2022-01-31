package ru.yajaneya.Spring2Geekbrains.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yajaneya.Spring2Geekbrains.core.converters.OrderConverter;
import ru.yajaneya.Spring2Geekbrains.core.dto.OrderDetailsDto;
import ru.yajaneya.Spring2Geekbrains.core.dto.OrderDto;
import ru.yajaneya.Spring2Geekbrains.core.entities.User;
import ru.yajaneya.Spring2Geekbrains.core.exceptions.ResourceNotFoundException;
import ru.yajaneya.Spring2Geekbrains.core.services.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final UserService userService;
    private final OrderConverter orderConverter;


    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findOrdersByUsername(principal.getName()).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(Principal principal, @RequestBody OrderDetailsDto orderDetailsDto) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        orderService.createOrder(user, orderDetailsDto);
    }
}
